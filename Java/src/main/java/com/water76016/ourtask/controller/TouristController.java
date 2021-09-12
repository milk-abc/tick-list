package com.water76016.ourtask.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water76016.ourtask.common.RestResult;
import com.water76016.ourtask.common.Utils;
import com.water76016.ourtask.dto.LoginTo;
import com.water76016.ourtask.entity.Tourist;
import com.water76016.ourtask.entity.User;
import com.water76016.ourtask.service.COSService;
import com.water76016.ourtask.service.RedisService;
import com.water76016.ourtask.service.TouristService;
import com.water76016.ourtask.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @program: our-task
 * @description: 针对游客对象能够调用接口的控制器
 * @author: water76016
 * @create: 2020-09-24 16:45
 **/
@Api(value = "游客控制", tags = {"游客操作"})
@RestController
public class TouristController {
    RSA rsa = new RSA();
    @Autowired
    TouristService touristService;
    @Autowired
    UserService userService;
    @Autowired
    COSService cosService;
    @Autowired
    RedisService redisService;

    @Value("${redis.database}")
    private String redisDatabase;
    @Value("${redis.securityCode}")
    private String redisSecurityCode;
    @Value("${redis.expire}")
    private long redisExpire;


    LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

    @ApiOperation("获取非对称加密公钥")
    @GetMapping("/getPublicKey")
    public RestResult getPublicKey(){
        return RestResult.success(rsa.getPublicKeyBase64());
    }

    @ApiOperation("获取加密后的信息")
    @GetMapping("/getPassword")
    public RestResult getEncode(String str, String publicKey){

        RSA r = new RSA(null, publicKey);
        String encode = r.encryptBcd(str, KeyType.PublicKey);
        return RestResult.success(encode);
    }

    @ApiOperation("游客进行注册操作")
    @PostMapping("/register")
    public RestResult register(@RequestBody @ApiParam("游客对象") Tourist tourist){
        if (StrUtil.isEmpty(tourist.getUsername())){
            RestResult.errorParams("注册用户：用户名不能为空");
        }
        if (StrUtil.isEmpty(tourist.getPassword())){
            RestResult.errorParams("注册用户：用户密码不能为空");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", tourist.getUsername());
        User user = userService.getOne(userQueryWrapper);
        if (user != null){
            return RestResult.errorParams("注册用户：用户名已存在");
        }
        String password = rsa.decryptStr(tourist.getPassword(), KeyType.PrivateKey);
        System.out.println("用户注册：" + tourist.getUsername() + ":" + password);
        tourist.setPassword(password);
        return touristService.register(tourist);
    }

    @ApiOperation("修改用户密码")
    @PostMapping("updatePassword")
    public RestResult updatePassword(@RequestBody @ApiParam("游客对象") Tourist tourist, HttpServletResponse response){
        String username = tourist.getUsername();
        String oldPassword = tourist.getOldPassword();
        String newPassword = tourist.getNewPassword();
        if (StrUtil.isEmpty(username)){
            return RestResult.errorParams("用户名为空");
        }
        if (StrUtil.isEmpty(oldPassword)){
            return RestResult.errorParams("旧密码不能为空");
        }
        if (StrUtil.isEmpty(newPassword)){
            return RestResult.errorParams("新密码不能为空");
        }
        //对用户名和密码进行解密
        oldPassword = rsa.decryptStr(oldPassword, KeyType.PrivateKey);
        newPassword = rsa.decryptStr(newPassword, KeyType.PrivateKey);
        //先判断用户名和密码是否匹配
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User user = userService.getOne(userQueryWrapper);
        if (user == null){
            return RestResult.notFindError("该用户不存在");
        }
        if (Utils.matches(oldPassword, user.getPassword()) == false){
            return RestResult.error("用户名和密码不匹配");
        }
        String password = Utils.encode(newPassword);
        user.setPassword(password);
        boolean flag = userService.updateById(user);
        return flag ? RestResult.success() : RestResult.error();
    }

    @ApiOperation("获取验证码")
    @GetMapping("/getSecurityCode")
    public RestResult getSecurityCode(HttpServletRequest request, HttpServletResponse response){
        lineCaptcha.createCode();
        String uuid = UUID.randomUUID().toString();
        //把验证码的键，放到Redis中
        String key = redisDatabase + ":" + redisSecurityCode + ":" + uuid;
        redisService.set(key, lineCaptcha.getCode());
        redisService.expire(key, redisExpire);
        //设置响应头
        response.setHeader("Access-Control-Expose-Headers", "securityCode");
        response.setHeader("securityCode", uuid);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //设置相应内容
        response.setContentType("image/jpeg");
        try {
            response.getOutputStream().write(lineCaptcha.getImageBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RestResult.success("获取验证码");
    }

    @ApiOperation("游客进行登录操作")
    @PostMapping({"/login"})
    public RestResult login(@RequestBody @ApiParam("游客对象") LoginTo loginTo, HttpServletResponse response) {
        if (StrUtil.isEmpty(loginTo.getUsername())){
            return RestResult.errorParams("用户登录：用户名不能为空");
        }
        if (StrUtil.isEmpty(loginTo.getPassword())){
            return RestResult.errorParams("用户登录：用户密码不能为空");
        }
        if (StrUtil.isEmpty(loginTo.getSecurityCodeKey()) || StrUtil.isEmpty(loginTo.getSecurityCodeValue())){
            return RestResult.errorParams("用户登录：验证码不能为空");
        }
        System.out.println("打印登录参数" + JSONUtil.toJsonPrettyStr(loginTo));
        String key = redisDatabase + ":" + redisSecurityCode + ":" + loginTo.getSecurityCodeKey();
        Object object = redisService.get(key);
        if (object == null){
            return RestResult.errorParams("验证码已失效，请重新输入");
        }
        String securityCodeValue = object.toString();
        if (StrUtil.equals(securityCodeValue, loginTo.getSecurityCodeValue()) == false){
            return RestResult.errorParams("验证码输入错误，请重新输入");
        }

        String password = rsa.decryptStr(loginTo.getPassword(), KeyType.PrivateKey);
        loginTo.setPassword(password);
        return touristService.login(loginTo, response);
    }
}

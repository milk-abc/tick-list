package com.water76016.ourtask.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water76016.ourtask.common.RestResult;
import com.water76016.ourtask.common.Utils;
import com.water76016.ourtask.config.security.jwt.JwtAuthService;
import com.water76016.ourtask.entity.Tourist;
import com.water76016.ourtask.entity.User;
import com.water76016.ourtask.service.TouristService;
import com.water76016.ourtask.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.PublicKey;


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

    @ApiOperation("游客进行登录操作")
    @PostMapping({"/login"})
    public RestResult login(@RequestBody @ApiParam("游客对象") Tourist tourist, HttpServletResponse response) {
        if (StrUtil.isEmpty(tourist.getUsername())){
            RestResult.errorParams("用户登录：用户名不能为空");
        }
        if (StrUtil.isEmpty(tourist.getPassword())){
            RestResult.errorParams("用户登录：用户密码不能为空");
        }
        String password = rsa.decryptStr(tourist.getPassword(), KeyType.PrivateKey);
        tourist.setPassword(password);
        return touristService.login(tourist, response);
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

}

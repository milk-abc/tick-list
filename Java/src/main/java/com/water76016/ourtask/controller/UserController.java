package com.water76016.ourtask.controller;

import cn.hutool.core.util.StrUtil;
import com.water76016.ourtask.common.RestResult;
import com.water76016.ourtask.common.Utils;
import com.water76016.ourtask.common.constant.Common;
import com.water76016.ourtask.entity.User;
import com.water76016.ourtask.service.COSService;
import com.water76016.ourtask.service.CategoryService;
import com.water76016.ourtask.service.TaskService;
import com.water76016.ourtask.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author github:water76016
 * @since 2020-07-21
 */
@Api(value = "用户控制", tags = {"用户操作"})
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    COSService cosService;

    @ApiOperation("修改用户基本信息")
    @PostMapping("update/{id}")
    public RestResult update(@RequestBody @ApiParam("用户对象") User user){
        if (StrUtil.isEmpty(user.getUsername())){
            return RestResult.errorParams("用户名不能为空");
        }
        if (StrUtil.isEmpty(user.getPassword())){
            return RestResult.errorParams("用户密码不能为空");
        }
        boolean flag = userService.updateById(user);
        return flag ? RestResult.success() : RestResult.error();
    }

    @ApiOperation("根据id返回一个用户信息")
    @GetMapping("/get/{id}")
    public RestResult get(@PathVariable @ApiParam("用户id") Integer id){
        User user = userService.getById(id);
        if (user == null){
            return RestResult.notFindError("该用户不存在");
        }
        return RestResult.success(user);
    }

    @CrossOrigin
    @ApiOperation("用户上传图片")
    @PostMapping(value = "/uploadPicture/{id}")
//    @PostMapping(value = "/uploadPicture/{id}",headers = "content-type=multipart/form-data")
    public RestResult uploadPicture(@RequestParam("multipartFile") @ApiParam("图片") MultipartFile multipartFile,
                                    @PathVariable @ApiParam("用户id") Integer id){
        //进行文件名校验
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        if (Common.ALLOW_PICTURE_TYPE.contains(suffix) == false){
            return RestResult.error("上传的文件名不满足要求，请重新上传");
        }
        if (multipartFile == null){
            return RestResult.error("上传文件为空");
        }
        RestResult result = cosService.putFile(id, multipartFile);
        return result;
    }

    @ApiOperation("用户获取头像url")
    @GetMapping("/getHeadPortrait/{id}")
    public RestResult getHeadPortrait(@PathVariable @ApiParam("用户id") Integer id){
        RestResult restResult = userService.getHeadPortrait(id);
        return restResult;
    }
}

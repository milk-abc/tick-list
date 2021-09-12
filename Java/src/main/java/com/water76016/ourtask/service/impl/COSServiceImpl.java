package com.water76016.ourtask.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.lang.UUID;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.water76016.ourtask.common.RestResult;
import com.water76016.ourtask.service.COSService;
import com.water76016.ourtask.service.RedisService;
import com.water76016.ourtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @program: our-task
 * @description:
 * @author: water76016
 * @create: 2021-08-25 23:56
 **/
@Service
public class COSServiceImpl implements COSService {
    @Value("${cos.secretId}")
    String secretId;
    @Value("${cos.secretKey}")
    String secretKey;
    @Value("${cos.regionName}")
    String regionName;
    @Value("${cos.bucketName}")
    String bucketName;
    @Value("${cos.imageAddr}")
    String imageAddr;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;





    @Override
    public RestResult putFile(Integer id, MultipartFile multipartFile) {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        String newFileName = UUID.randomUUID().toString() + suffix;
        File file = null;
        try {
            file = File.createTempFile(newFileName, null);
            multipartFile.transferTo(file);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newFileName, file);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return RestResult.error("上传头像失败");
        }
        String url = imageAddr + newFileName;
        boolean flag = userService.updateImageUrl(id, url);
        if (flag == false){
            return RestResult.error("头像URL存储失败");
        }
        return RestResult.success("上传图片成功",url);
    }


}

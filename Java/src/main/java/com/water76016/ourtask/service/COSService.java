package com.water76016.ourtask.service;

import com.water76016.ourtask.common.RestResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface COSService {
    RestResult putFile(Integer id, MultipartFile file);
}

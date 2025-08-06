package com.album.controller;


import com.album.common.Result;
import com.album.utils.S3Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {


    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = S3Utils.upload(file);
            return Result.success(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("Failed to upload: " + e.getMessage());
        }
    }
}

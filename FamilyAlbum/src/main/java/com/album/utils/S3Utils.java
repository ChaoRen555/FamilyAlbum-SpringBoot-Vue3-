package com.album.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


@Component
public class S3Utils {

    private static String bucketName;
    private static String baseUrl;
    private static S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    public void setBucketName(String bucketName) {
        S3Utils.bucketName = bucketName;
    }

    @Value("${aws.s3.base-url}")
    public void setBaseUrl(String baseUrl) {
        S3Utils.baseUrl = baseUrl;
    }

    public S3Utils(S3Client s3Client) {
        S3Utils.s3Client = s3Client;
    }


    public static String upload(MultipartFile file) throws Exception {
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

        return baseUrl + "/" + fileName;
    }


    public static void delete(String url){
        String objectKey = url.substring(url.lastIndexOf("/") + 1);
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        s3Client.deleteObject(request);
    }
}

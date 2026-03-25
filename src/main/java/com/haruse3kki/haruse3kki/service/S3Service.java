package com.haruse3kki.haruse3kki.service;

import com.haruse3kki.haruse3kki.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file) {
        String fileName = "meals/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(fileName)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );
        } catch (IOException e) {
            throw new CustomException(500, "이미지 업로드 실패");
        }

        return "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
    }

    public void delete(String fileUrl) {

        if (fileUrl == null || !fileUrl.contains(".amazonaws.com/")) {
            return;
        }

        String key = fileUrl.substring(fileUrl.indexOf(".amazonaws.com/") + ".amazonaws.com/".length());

        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build()
        );
    }
}
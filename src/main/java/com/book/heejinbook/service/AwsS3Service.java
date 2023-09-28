package com.book.heejinbook.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.UtilErrorCode;
import com.book.heejinbook.utils.FilePathUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String uploadImage(MultipartFile uploadFile, String fileName) throws IOException {
        try (InputStream inputStream = uploadFile.getInputStream()) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(uploadFile.getSize());
            objectMetadata.setContentType(uploadFile.getContentType());
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
            );
            return amazonS3Client.getUrl(bucket, fileName).toString();
        }
    }

    public void removeFile(String imageUrl) {
        if (imageUrl == null) return;
        String filePath = FilePathUtils.convertImageUrlToFilePath(imageUrl);
        filePath = filePath.replace("%2F%2F", "/");
        if(!amazonS3Client.doesObjectExist(bucket, filePath)){
            throw new CustomException(UtilErrorCode.NOT_FOUND_FILE);
        }

        amazonS3Client.deleteObject(bucket, filePath);
    }


}

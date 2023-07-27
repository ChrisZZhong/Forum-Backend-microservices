package com.forum.fileservice.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Repository
public class FileDao {
    private AmazonS3 amazonS3Client;
    private ResourceLoader resourceLoader;

    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.region}")
    private String region;
    @Value("${defaultProfilePath}")
    private String defaultProfilePath;
    @Value("${defaultProfileURL}")
    private String defaultProfileURL;

    @Autowired
    public FileDao(ResourceLoader resourceLoader, AmazonS3 amazonS3Client) {
        this.resourceLoader = resourceLoader;
        this.amazonS3Client = amazonS3Client;
    }

    public URL getDefaultUserProfile() throws IOException {
        try {
            S3Object object = amazonS3Client.getObject(bucketName, defaultProfilePath);
            URL url = amazonS3Client.getUrl(bucketName, defaultProfilePath);
            return url;
        } catch (AmazonS3Exception e) {
            Resource resource = resourceLoader.getResource("classpath:defaultProfile.png");
            File fileObj = resource.getFile();
            amazonS3Client.putObject(new PutObjectRequest(bucketName, defaultProfilePath, fileObj));
            fileObj.delete();
            URL url = amazonS3Client.getUrl(bucketName, defaultProfilePath);
            return url;
        }
    }

    public URL putObject(String objectKey, File fileObj) {
        amazonS3Client.putObject(new PutObjectRequest(bucketName, objectKey, fileObj));
        URL url = amazonS3Client.getUrl(bucketName, objectKey);
        return url;
    }
}

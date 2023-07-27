package com.forum.fileservice.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.*;
import com.forum.fileservice.dao.FileDao;
import com.forum.fileservice.exception.ConversionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    private static final String userPrefix = "user/";
    private static final String postPrefix = "post/";
    private final FileDao fileDao;

    public FileService(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public URL getDefaultUserProfile() throws IOException {
        return fileDao.getDefaultUserProfile();
    }

    public URL updateUserProfileByUserId(String userId, MultipartFile file) throws ConversionException, SdkClientException  {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = UUID.randomUUID().toString()+"-"+file.getOriginalFilename();
        String objectKey = userPrefix + userId + "/" + fileName;
        URL url = fileDao.putObject(objectKey, fileObj);
        fileObj.delete();
        return url;
    }

    public URL createPostAttachment(String postId, MultipartFile file) throws ConversionException, SdkClientException {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = UUID.randomUUID().toString()+"-"+file.getOriginalFilename();
        String objectKey = postPrefix + postId + "/" + fileName;
        URL url = fileDao.putObject(objectKey, fileObj);
        fileObj.delete();
        return url;
    }

    public List<URL> updatePostAttachments(String postId, List<MultipartFile> fileList) throws ConversionException {
        List<URL> attachmentURLList = new ArrayList<>();
        for(MultipartFile file : fileList){
            URL postAttachment = createPostAttachment(postId, file);
            attachmentURLList.add(postAttachment);
        }
        return attachmentURLList;
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws ConversionException {
        File convertedFile = new File(file.getOriginalFilename());
        try{
            FileOutputStream fos = new FileOutputStream(convertedFile);
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new ConversionException("Failed to convert file appropriately provided by the request.");
        }
        return convertedFile;
    }

}

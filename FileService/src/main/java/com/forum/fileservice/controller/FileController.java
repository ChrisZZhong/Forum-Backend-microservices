package com.forum.fileservice.controller;

import com.forum.fileservice.dto.response.URLResponse;
import com.forum.fileservice.exception.ConversionException;
import com.forum.fileservice.service.FileService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@RestController
public class FileController {
    private final FileService fileService;
    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/health")
    public ResponseEntity<?> getHealth(){
        return ResponseEntity.ok("health");
    }

    /**
     * 1. getting default profileImage for newly created user
     * @return RepsonseEntity of URLResponse
     * @throws IOException
     */
    @GetMapping("/default")
    public ResponseEntity<URLResponse> getDefaultUserProfile() throws IOException {
        URL objectURL = fileService.getDefaultUserProfile();
        URLResponse urlResponse = URLResponse
                .builder()
                .status(String.valueOf(HttpStatus.OK))
                .urlList(new ArrayList<>(Arrays.asList(objectURL)))
                .build();
        return ResponseEntity.ok(urlResponse);
    }

    /**
     * 2. update the user profileImage with userId
     * @param userId
     * @param file
     * @return RepsonseEntity of URLResponse
     */
    @PatchMapping(value = "/users/{userId}" , consumes = "multipart/form-data")
    public ResponseEntity<URLResponse> updateUserProfileByUserId(@PathVariable String userId, @RequestBody MultipartFile file) throws ConversionException {
        if(file==null)return ResponseEntity.ok(new URLResponse("Empty file received", new ArrayList<>()));
        URL objectURL = fileService.updateUserProfileByUserId(userId, file);
        URLResponse urlResponse = URLResponse
                .builder()
                .status(String.valueOf(HttpStatus.OK))
                .urlList(new ArrayList<>(Arrays.asList(objectURL)))
                .build();
        return ResponseEntity.ok(urlResponse);
    }

    /**
     * 3. create attachment for the post with postId
     * @param file
     * @param postId
     * @return
     * @throws FileSizeLimitExceededException
     */
    @PostMapping(value = "/posts/{postId}", consumes = "multipart/form-data")
    public ResponseEntity<URLResponse> addAttachmentByPostId(@RequestBody MultipartFile file, @PathVariable(value = "postId") String postId) throws ConversionException {
        if(file==null)return ResponseEntity.ok(new URLResponse("Empty file received", new ArrayList<>()));
        URL objectURL = fileService.createPostAttachment(postId, file);
        URLResponse urlResponse = URLResponse
                .builder()
                .status(String.valueOf(HttpStatus.OK))
                .urlList(new ArrayList<>(Arrays.asList(objectURL)))
                .build();
        return ResponseEntity.ok(urlResponse);
    }

    /**
     * 4. create a list of attachments for the post with postId
     * @param postId
     * @param fileList
     * @return
     */
    @PostMapping(value = "posts/{postId}/filelist", consumes = "multipart/form-data")
    public ResponseEntity<URLResponse> addAttachmentListByPostId(@PathVariable String postId, @ModelAttribute List<MultipartFile> fileList) throws ConversionException {
        if(fileList==null || fileList.size()==0)return ResponseEntity.ok(new URLResponse("Empty file received", new ArrayList<>()));
        List<URL> urlList = fileService.updatePostAttachments(postId, fileList);
        URLResponse urlResponse = URLResponse
                .builder()
                .status(String.valueOf(HttpStatus.OK))
                .urlList(urlList)
                .build();
        return ResponseEntity.ok(urlResponse);
    }
}

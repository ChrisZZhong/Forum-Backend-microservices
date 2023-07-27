package com.forum.authenticationservice.service.remote;

import com.forum.authenticationservice.config.FeignRequestInterceptor;
import com.forum.authenticationservice.dto.request.NewUserRequest;
import com.forum.authenticationservice.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user", configuration = FeignRequestInterceptor.class)
public interface RemoteUserService {
    @GetMapping(value = "user/info")
    ResponseEntity<UserResponse> loadUserByUsername(@RequestParam String username);

    @PostMapping("user/")
    ResponseEntity<UserResponse> addNewUser(@RequestBody NewUserRequest newUserRequest);
    /*
    add a new user to the database
    NewUserRequest
        private String email;
        private String password;
        private String firstname;
        private String lastname;
        private String profileImageURL;
     */

    @PatchMapping("user/type")
    ResponseEntity<UserResponse> updateUserByType(@RequestParam String email, @RequestBody String type);
    /*
    update a user's type
    return the updated user
     */

    @PatchMapping(value = "user/active")
    ResponseEntity<UserResponse> changeBanStatus(@RequestBody String email);

    @PatchMapping(value = "user/email")
    ResponseEntity<UserResponse> updateUserByEmail(@RequestBody String newEmail);

}

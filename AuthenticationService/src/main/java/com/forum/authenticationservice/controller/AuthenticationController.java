package com.forum.authenticationservice.controller;

import com.forum.authenticationservice.cache.Token;
import com.forum.authenticationservice.dto.request.*;
import com.forum.authenticationservice.dto.response.MessageResponse;
import com.forum.authenticationservice.dto.response.TokenResponse;
import com.forum.authenticationservice.exception.AlreadyVerifiedException;
import com.forum.authenticationservice.exception.ErrorTokenException;
import com.forum.authenticationservice.exception.SignUpFailedException;
import com.forum.authenticationservice.security.AuthUserDetail;
import com.forum.authenticationservice.security.JwtProvider;
import com.forum.authenticationservice.service.AuthenticationService;
import com.forum.authenticationservice.util.tokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) throws BadCredentialsException, UsernameNotFoundException {
        String jwtToken = authenticationService.generateToken(request.getEmail(), request.getPassword(), authenticationManager);
        return ResponseEntity.ok(
                TokenResponse.builder()
                        .status("success")
                        .message("Welcome " + request.getEmail())
                        .token(jwtToken)
                        .build()
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<TokenResponse> signup(@Valid @RequestBody NewUserRequest newUserRequest) throws SignUpFailedException {
        String jwtToken = authenticationService.signUp(newUserRequest, authenticationManager);
        return ResponseEntity.ok(
                TokenResponse.builder()
                        .status("success")
                        .message("successfully registered, please check your email for validation")
                        .token(jwtToken)
                        .build()
        );
    }

    @PostMapping("/validation")
    @PreAuthorize("hasAuthority('unverified')")
    public ResponseEntity<TokenResponse> validateEmail(@Valid @RequestBody EmailValidationRequest emailValidationRequest, @AuthenticationPrincipal AuthUserDetail authUserDetail) throws ErrorTokenException {
        String jwtToken = authenticationService.verifyEmail(authUserDetail.getUsername(), emailValidationRequest.getValidationToken(), authenticationManager);
        return ResponseEntity.ok(
                TokenResponse.builder()
                        .status("success")
                        .message("Email successfully validated")
                        .token(jwtToken)
                        .build()
        );
    }

    @PatchMapping("/email")
    @PreAuthorize("hasAnyAuthority('unverified', 'normal', 'admin', 'super')")
    public ResponseEntity<TokenResponse> changeEmail(@Valid @RequestBody EmailChangeRequest emailChangeRequest, @AuthenticationPrincipal AuthUserDetail authUserDetail) throws SignUpFailedException {
        String jwtToken = authenticationService.changeEmail(authUserDetail.getUsername(), emailChangeRequest.getNewEmail(), authenticationManager);
        return ResponseEntity.ok(
                TokenResponse.builder()
                        .status("success")
                        .message("successfully send update email, check your email and finish the process")
                        .token(jwtToken)
                        .build()
        );
    }

    @PatchMapping("/resend")
    @PreAuthorize("hasAuthority('unverified')")
    public ResponseEntity<MessageResponse> resendEmail(@RequestBody EmailChangeRequest emailChangeRequest) throws AlreadyVerifiedException {
        authenticationService.resendEmail(emailChangeRequest.getNewEmail());
        return ResponseEntity.ok(
                MessageResponse.builder()
                        .status("success")
                        .message("successfully resent email, please check your email")
                        .build()
        );
    }

}

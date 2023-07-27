package com.forum.authenticationservice.service;
import com.forum.authenticationservice.Entity.User;
import com.forum.authenticationservice.cache.ValidationTokenCache;
import com.forum.authenticationservice.dto.request.NewUserRequest;
import com.forum.authenticationservice.dto.response.UserResponse;
import com.forum.authenticationservice.exception.AlreadyVerifiedException;
import com.forum.authenticationservice.exception.BannedUserException;
import com.forum.authenticationservice.exception.ErrorTokenException;
import com.forum.authenticationservice.exception.SignUpFailedException;
import com.forum.authenticationservice.security.AuthUserDetail;
import com.forum.authenticationservice.security.JwtProvider;
import com.forum.authenticationservice.service.remote.RemoteUserService;
import com.forum.authenticationservice.util.tokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// this service is used for authentication
@Service
public class AuthenticationService implements UserDetailsService {
    private RemoteUserService userService;

    private JwtProvider jwtProvider;

    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setUserService(RemoteUserService userService) {
        this.userService = userService;
    }

    public User getUserByEmail(String email) throws BadCredentialsException{
        ResponseEntity<UserResponse> userResponse = userService.loadUserByUsername(email);
        if (userResponse.getBody().getUser() == null) {
            throw new BadCredentialsException("Username not registered");
        }
        return Objects.requireNonNull(userResponse.getBody()).getUser();
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = getUserByEmail(username);
        if (Objects.equals(user.getType(), "banned")) {
            throw new BadCredentialsException("User is banned");
        }
        return AuthUserDetail.builder() // spring security's userDetail
                .username(user.getEmail())
                .userId(user.getUserId())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }
    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();
        userAuthorities.add(new SimpleGrantedAuthority(user.getType()));
        return userAuthorities;
    }

    public String generateToken(String email, String password, AuthenticationManager authenticationManager) {
        Authentication authentication;
        //Try to authenticate the user using the email and password
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (AuthenticationException e){
            System.out.println(e.getMessage());
            if (e.getMessage().equals("User is banned")) throw new BadCredentialsException("User is banned");
            if (e.getMessage().equals("Username not registered")) throw new BadCredentialsException("Username not registered");
            throw new BadCredentialsException("Incorrect credentials, please try again.");
        }

        //Successfully authenticated user will be stored in the authUserDetail object
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object

        // create and store token in cache
        return jwtProvider.createToken(authUserDetail);
    }

    @Transactional
    public String signUp(NewUserRequest newUserRequest, AuthenticationManager authenticationManager) throws SignUpFailedException {
        String jwtToken;
        try {
            // call user service add new user
            User user = Objects.requireNonNull(userService.addNewUser(newUserRequest).getBody()).getUser();
            if (user == null) {
                throw new SignUpFailedException("user already exists");
            }
            // call email service to send email
            emailService.sendValidationEmail(user.getEmail());
            // generate token
            jwtToken = generateToken(user.getEmail(), user.getPassword(), authenticationManager);
        } catch (Exception e) {
            throw new SignUpFailedException("Sign up failed");
        }
        return jwtToken;
    }

    public String verifyEmail(String email, String token, AuthenticationManager authenticationManager) throws ErrorTokenException {
        User user = getUserByEmail(email);
        tokenUtil.isTokenValid(email, token);
        userService.updateUserByType(email, "normal");
        ValidationTokenCache.removeToken(email);
        return generateToken(user.getEmail(), user.getPassword(), authenticationManager);
    }

    public String changeEmail(String email, String newEmail, AuthenticationManager authenticationManager) throws SignUpFailedException {
        User user = getUserByEmail(email);
        if (Objects.equals(userService.updateUserByEmail(newEmail).getBody().getStatus(), "failed")) {
            throw new SignUpFailedException("user already exists");
        }
        emailService.sendValidationEmail(newEmail);
        userService.updateUserByType(newEmail, "unverified");
        return generateToken(newEmail, user.getPassword(), authenticationManager);
    }


    public void resendEmail(String email) throws AlreadyVerifiedException {
        User user = getUserByEmail(email);
        if (!user.getType().equals("unverified")) {
            throw new AlreadyVerifiedException("User already verified email");
        }
        emailService.sendValidationEmail(email);
    }
}

package com.forum.authenticationservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attr = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attr.getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token)) {
            template.header("Authorization", token);
        }
    }
}
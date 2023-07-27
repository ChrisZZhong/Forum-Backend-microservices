package com.forum.authenticationservice.util;

import com.forum.authenticationservice.cache.Token;
import com.forum.authenticationservice.cache.ValidationTokenCache;
import com.forum.authenticationservice.exception.ErrorTokenException;

import javax.servlet.http.HttpServletRequest;

public class tokenUtil {

    public static void isTokenValid(String email, String token) throws ErrorTokenException {
        if (!ValidationTokenCache.containsToken(email)) throw new ErrorTokenException("code doesn't exist please request a new code");
        Token CacheToken = ValidationTokenCache.getToken(email);
        if (!token.equals(CacheToken.getToken())) throw new ErrorTokenException("Invalid code");
        if (CacheToken.isExpired()) throw new ErrorTokenException("Code expired, please request a new code");
    }
}

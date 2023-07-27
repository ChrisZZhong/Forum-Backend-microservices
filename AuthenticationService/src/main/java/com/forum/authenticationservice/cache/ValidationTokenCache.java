package com.forum.authenticationservice.cache;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ValidationTokenCache {
    public static Map<String, Token> tokenMap = new HashMap<>();

    public static void addToken(String email, Token token) {
        tokenMap.put(email, token);
    }

    public static boolean containsToken(String email) {
        return tokenMap.containsKey(email);
    }

    public static Token getToken(String email) {
        return tokenMap.get(email);
    }

    public static void updateToken(String email, Token token) {
        tokenMap.replace(email, token);
    }

    public static void removeToken(String email) {
        tokenMap.remove(email);
    }
}

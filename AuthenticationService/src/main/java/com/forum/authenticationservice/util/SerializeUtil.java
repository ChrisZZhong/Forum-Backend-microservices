package com.forum.authenticationservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forum.authenticationservice.dto.request.EmailValidationMessage;

public class SerializeUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String serialize(EmailValidationMessage message){

        String result = null;

        try {
            result = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

}

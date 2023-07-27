package com.forum.authenticationservice.service;

import com.forum.authenticationservice.cache.Token;
import com.forum.authenticationservice.cache.ValidationTokenCache;
import com.forum.authenticationservice.dto.request.EmailValidationMessage;
import com.forum.authenticationservice.util.SerializeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendValidationEmail(String email) {
        // check if token exist, make sure store a valid token
        if (ValidationTokenCache.containsToken(email)) {
            if (ValidationTokenCache.getToken(email).isExpired()) {
                // expired update new token
                ValidationTokenCache.updateToken(email, new Token());
            }
        } else {
            // not exist generate new token
            ValidationTokenCache.addToken(email, new Token());
        }
        // get valid token
        String validationToken = ValidationTokenCache.getToken(email).getToken();
        // send message to rabbitmq
        String message = "Your validation token is: " + validationToken;
        EmailValidationMessage emailValidationMessage = EmailValidationMessage.builder()
                .email(email)
                .message(message)
                .build();
        String jsonMessage = SerializeUtil.serialize(emailValidationMessage);
        rabbitTemplate.convertAndSend("forum.direct.exchange", "direct.routing.key", jsonMessage);
    }

}

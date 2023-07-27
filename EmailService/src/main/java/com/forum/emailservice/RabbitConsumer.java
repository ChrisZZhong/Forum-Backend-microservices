package com.forum.emailservice;

import com.forum.emailservice.dto.EmailValidationMessage;
import com.forum.emailservice.service.EmailService;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {
    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "direct.queue")
    public void consumeMessage(String message){
        Gson gson = new Gson();
        EmailValidationMessage emailValidationMessage = gson.fromJson(message, EmailValidationMessage.class);

        emailService.sendEmail(emailValidationMessage.getEmail(), "Email Validation", emailValidationMessage.getMessage() + "\n http://localhost:3000/users/verify");
        System.out.println("Message Received: " + emailValidationMessage);
    }
}

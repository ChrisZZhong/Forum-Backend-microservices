package com.forum.messageservice.service;

import com.forum.messageservice.dao.MessageDao;
import com.forum.messageservice.domain.Message;
import com.forum.messageservice.dto.request.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private MessageDao messageDao;
    @Autowired
    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Transactional
    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    @Transactional
    public Message getMessageById(int id){
        return messageDao.findById(id);
    }

    @Transactional
    public void updateMessage(Message message){
        messageDao.update(message);
    }

    @Transactional
    public void addMessage(Message... messages) {
        for (Message m : messages) {
            messageDao.addMessage(m);
        }
    }
    @Transactional
    public Message sendMessage(MessageRequest request) {
        Message message = Message.builder()
                .userId(request.getUserId())
                .email(request.getEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .date(LocalDateTime.now().toString())
                .status("Open")
                .build();
        addMessage(message);
        return message;
    }
    @Transactional
    public Message changeStatus(int messageId) {
        Message message = getMessageById(messageId);
        if (message.getStatus().equals("Open")) {
            message.setStatus("Close");
        } else {
            message.setStatus("Open");
        }
        messageDao.update(message);
        return message;
    }
}

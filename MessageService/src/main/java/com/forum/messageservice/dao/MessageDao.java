package com.forum.messageservice.dao;

import com.forum.messageservice.domain.Message;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MessageDao extends AbstractHibernateDao<Message> {
    public MessageDao() { setClazz(Message.class);}

    public List<Message> getAllMessages() {
        return this.getAll();
    }

    public void addMessage(Message message) {
        add((Message) message);
    }
}

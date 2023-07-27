package com.forum.userservice.dao;

import com.forum.userservice.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO extends AbstractHibernateDao<User>{
    public UserDAO(){
        setClazz(User.class);
    }
    public List<User> getAllUsers(){
        return this.getAll();
    }
    
    public Optional<User> getUserByEmail(String email){
        List<User> users = getAllUsers();
        return users.stream().filter(user -> email.equals(user.getEmail())).findAny();
    }
}

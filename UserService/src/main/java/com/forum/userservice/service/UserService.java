package com.forum.userservice.service;

import com.forum.userservice.dao.UserDAO;
import com.forum.userservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public List<User> loadAllUsers(){
        return userDAO.getAllUsers();
    }
    
    @Transactional
    public User getUserByUsername(String username){
        Optional<User> user = userDAO.getUserByEmail(username);
        return user.orElse(null);
    }
    
    @Transactional
    public User getUserById(int id){
        return userDAO.findById(id);
    }
    
    @Transactional
    public User addNewUser(User user){
        String username = user.getEmail();
        Optional<User> ou = userDAO.getUserByEmail(username);
        if (ou.isPresent()) return null;
        userDAO.add(user);
        Optional<User> newOU = userDAO.getUserByEmail(username);
        return newOU.orElse(null);
    }
    
    @Transactional
    public User updateUserType(String username, String type){
        Optional<User> ou = userDAO.getUserByEmail(username);
        if (ou.isPresent()){
            User user = ou.get();
            if (type.equals("normal")){
                user.setActive(true);
            }
            if (type.equals("unverified")){
                user.setActive(false);
            }
            user.setType(type);
            userDAO.update(user);
            return user;
        }
        else return null;
    }
    
    @Transactional
    public User changeBanStatus(String username){
        Optional<User> ou = userDAO.getUserByEmail(username);
        if (ou.isPresent()){
            User user = ou.get();
            if (user.getType().equals("banned")){
                if (user.isActive()){
                    user.setType("normal");
                }
                else {
                    user.setType("unverified");
                }
            }
            else {
                user.setType("banned");
            }
            userDAO.update(user);
            return user;
        }
        else return null;
    }

    @Transactional
    public User changeAdminStatus(String username){
        Optional<User> ou = userDAO.getUserByEmail(username);
        if (ou.isPresent()){
            User user = ou.get();
            if (user.getType().equals("admin")){
                if (user.isActive()){
                    user.setType("normal");
                }
                else {
                    user.setType("unverified");
                }
            }
            else {
                user.setType("admin");
            }
            userDAO.update(user);
            return user;
        }
        else return null;
    }
    
    @Transactional
    public User updateUserEmail(String username, String email){
        Optional<User> check = userDAO.getUserByEmail(email);
        if (check.isPresent()) return null;
        Optional<User> ou = userDAO.getUserByEmail(username);
        if (ou.isPresent()){
            User user = ou.get();
            user.setEmail(email);
            userDAO.update(user);
            return user;
        }
        else return null;
    }
    
    @Transactional
    public List<User> loadUsersByIdList(List<Integer> list){
        List<User> res = new ArrayList<>();
        for (int id : list){
            User user = userDAO.findById(id);
            res.add(User.builder()
                    .userId(user.getUserId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .profileImageURL(user.getProfileImageURL())
                    .build());
        }
        return res;
    }

    @Transactional
    public User updateUserImageURL(int id, String imageURL) {
        User user = userDAO.findById(id);
        user.setProfileImageURL(imageURL);
        userDAO.update(user);
        return user;
    }
}

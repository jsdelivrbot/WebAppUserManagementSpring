package com.massimo.webapp.service;

import com.massimo.webapp.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserService {

    @Transactional
    List<User> getAllUsers();

    @Transactional
    void deleteUser(int id);

    @Transactional
    void saveUser(User user);

    User findUserById(int id);

    void updateUser(User user);

    List<User> searchUsers(String searchString);


}

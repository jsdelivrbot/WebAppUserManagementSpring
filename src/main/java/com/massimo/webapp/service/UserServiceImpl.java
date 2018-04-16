package com.massimo.webapp.service;

import com.massimo.webapp.model.User;
import com.massimo.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Transactional
    public List<User> getAllUsers(){
        List<User> usersList =(List<User>) userRepository.findAll();
        return usersList;
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserById(int id) {
        User user = userRepository.findOne(id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        User userUpdated = userRepository.findOne(user.getId());
        if(userUpdated!=null) {
            userUpdated.setFirstname(user.getFirstname());
            userUpdated.setLastname(user.getLastname());
            userUpdated.setBirthDate(user.getBirthDate());
            userUpdated.setCountry(user.getCountry());
            userUpdated.setMaritalStatus(user.getMaritalStatus());
            userUpdated.setSkills(user.getSkills());
        }
        userRepository.save(userUpdated);
    }

    @Override
    public List<User> searchUsers(String searchString) {
        List<User> searchedUserList = userRepository.findByFirstnameOrLastnameOrCountryContaining(searchString);
        return searchedUserList;
    }



}

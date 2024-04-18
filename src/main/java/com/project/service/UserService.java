package com.project.service;

import com.project.pojo.User;

import javax.transaction.Transactional;

@Transactional
public interface UserService {

    User login(String username, String password);

    void register(User user);

    boolean checkUserExist(String username);

}

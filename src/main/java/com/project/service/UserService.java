package com.project.service;

import com.project.pojo.User;

public interface UserService {

    User login(String username, String password);

    void register(User user);

    boolean checkUserExist(String username);

}

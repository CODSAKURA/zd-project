package com.project.service;

import com.project.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Map;

@Transactional
public interface UserService {

    boolean login(String username, String password, HttpServletRequest request);

    boolean register(Map<String, Object> parameters, HttpServletRequest request);

    boolean checkUserExist(String username);

    boolean codeGenerate(HttpServletRequest request, HttpServletResponse response);
}

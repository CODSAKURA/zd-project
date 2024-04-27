package com.project.controller;

import com.project.pojo.User;
import com.project.service.UserService;
import com.project.util.CheckCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user")// TODO 需要分析接口的合理性
public class UserController {
    @Autowired
    private CheckCodeUtils checkCodeUtils;

    @Autowired
    private UserService userService;

    /**
     * 生成验证码
     */
    @RequestMapping("/checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 创建Session
        HttpSession session = request.getSession();

        // 生成验证码
        ServletOutputStream os = response.getOutputStream();
        String checkCode = checkCodeUtils.outputVerifyImage(100, 50, os, 4);

        // 将验证码存在Session中
        session.setAttribute("checkCodeGen", checkCode);
    }

    /**
     * 用户登录
     */
    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestBody User user, HttpServletRequest request) throws IOException {
        // 获取用户信息
        User userTest = userService.login(user.getUsername(), user.getPassword());

        // 用户不存在，登录失败
        if(userTest == null){
            return "login_failed";
        }

        // 用户存在，登录成功
        HttpSession session = request.getSession();
        session.setAttribute("user",userTest);
        return "login_success";
    }

    /**
     * 用户注册
     */
    @RequestMapping("/register")
    @ResponseBody
    public String register(@RequestBody Map<String, Object> parameters, HttpServletRequest request) throws IOException {
        // 获取输入的验证码
        String code = (String) parameters.get("code");

        // 从Session中获取程序生成的验证码
        HttpSession session = request.getSession();
        String checkCodeGen = (String) session.getAttribute("checkCodeGen");

        // 验证码不正确，注册失败
        if (!checkCodeGen.equalsIgnoreCase(code)){
            return "register_failed";
        }

        // 验证码正确，开始注册
        // 接收用户名和密码
        String username = (String) parameters.get("username");
        String password = (String) parameters.get("password");

        // 封装成User对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // 注册用户
        userService.register(user);

        // 返回注册成功信息
        return "register_success";
    }

    /**
     * 检查用户名是否存在
     */
    @RequestMapping("/selectUser")
    @ResponseBody
    public String selectUser(String username){
        //调用方法
        boolean flag = userService.checkUserExist(username);

        //获取数据
        return "" + flag;
    }
}

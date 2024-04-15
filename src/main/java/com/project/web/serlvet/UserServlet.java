package com.project.web.serlvet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.project.pojo.User;
import com.project.service.UserService;
import com.project.service.impl.UserServiceImpl;
import com.project.util.CheckCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@Service
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    @Autowired
    //统一：创建userService对象
    private UserService userService;// FIXME：Spring注入失败，无法注入UserService对象

    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建Session
        HttpSession session = request.getSession();

        // 生成验证码
        ServletOutputStream os = response.getOutputStream();
        String checkCode = CheckCodeUtil.outputVerifyImage(100, 50, os, 4);

        // 将验证码存在Session中
        session.setAttribute("checkCodeGen", checkCode);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取JSON请求体数据
        BufferedReader reader = request.getReader();
        String params = reader.readLine();

        // 使用 FastJson 将 JSON 字符串转换为 Map
        Map<String, Object> jsonMap = JSON.parseObject(params, new TypeReference<Map<String, Object>>() {});

        // 从 Map 中提取出 username 和 password
        String username = (String) jsonMap.get("username");
        String password = (String) jsonMap.get("password");

        // 执行方法
        User userTest = userService.login(username, password);

        // 判断
        if (userTest != null) {
            // 将用户信息存储在Session中
            HttpSession session = request.getSession();
            session.setAttribute("user",userTest);

            // 登陆成功
            response.getWriter().write("login_success");
        } else {
            // 登陆失败
            response.getWriter().write("login_failed");
        }
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取JSON请求体数据
        BufferedReader reader = request.getReader();
        String params = reader.readLine();

        // 使用 FastJson 将 JSON 字符串转换为 Map
        Map<String, Object> jsonMap = JSON.parseObject(params, new TypeReference<Map<String, Object>>() {});
        String code = (String) jsonMap.get("code");

        // 获取程序生成的验证码（从Session中）
        HttpSession session = request.getSession();
        String checkCodeGen = (String) session.getAttribute("checkCodeGen");

        // 比对
        if (checkCodeGen.equalsIgnoreCase(code)) {
            // Request配置【接收用户名和密码】
            String username = (String) jsonMap.get("username");
            String password = (String) jsonMap.get("password");

            // 封装成User对象
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            // 验证码通过，便注册的后续操作
            userService.register(user);

            //注册成功（给提示信息，并跳转到登陆页面）
            response.getWriter().write("register_success");
        }
        else
        {
            response.getWriter().write("register_failed");
        }
    }

    public void selectUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收用户名
        String username = request.getParameter("username");

        //调用方法
        boolean flag = userService.checkUserExist(username);

        //获取数据
        response.getWriter().write("" + flag);
    }
}


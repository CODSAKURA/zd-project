package com.project.controller;

import com.project.enums.UserResponseCode;
import com.project.frontend.FrontendResponseResult;
import com.project.service.UserService;
import com.project.util.codeGenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 控制层：用户类（Rest风格）
 *
 * @author : 周迪
 * @date : 2024/04/28
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 生成验证码
     */
    @GetMapping("/codeGenerate")
    public FrontendResponseResult codeGenerate(HttpServletRequest request, HttpServletResponse response){
        boolean flag = userService.codeGenerate(request, response);

        // 封装成FrontendResponseResult传给前端
        return new FrontendResponseResult(flag ? UserResponseCode.CODE_GENERATE_OK.getCode() : UserResponseCode.CODE_GENERATE_ERROR.getCode());
    }

    /**
     * 用户登录
     */
    @GetMapping("/username/{username}/password/{password}")
    public FrontendResponseResult login(@PathVariable String username, @PathVariable String password, HttpServletRequest request) {
        // 判断用户是否存在
        boolean flag = userService.login(username, password, request);

        // 封装成FrontendResponseResult传给前端
        return new FrontendResponseResult(flag ? UserResponseCode.LOGIN_OK.getCode() : UserResponseCode.LOGIN_ERROR.getCode());
    }

    /**
     * 用户注册
     * - 如验证码输入错误等，统一为注册失败
     */
    @PostMapping
    public FrontendResponseResult register(@RequestBody Map<String, Object> parameters, HttpServletRequest request) throws IOException {
        // 注册用户
        boolean registerStatus = userService.register(parameters, request);

        // 判断注册的结果，封装成FrontendResponseResult传给前端
        return new FrontendResponseResult(registerStatus ? UserResponseCode.REGISTER_OK.getCode() : UserResponseCode.REGISTER_ERROR.getCode());
    }

    /**
     * 检查用户名是否存在
     */
    @GetMapping("/{username}")
    public FrontendResponseResult selectUser(@PathVariable String username) {
        // 对用户名是否存在进行判断
        boolean flag = userService.checkUserExist(username);

        // 判断用户名是否存在的结果，封装成FrontendResponseResult传给前端
        return new FrontendResponseResult(flag ? UserResponseCode.USERNAME_EXIST_OK.getCode() : UserResponseCode.USERNAME_EXIST_ERROR.getCode());
    }
}

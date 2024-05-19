package pers.brand.web.controller;

import pers.brand.constant.UserConstant;
import pers.brand.dto.Result;
import pers.brand.domain.service.UserDomainService;
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
    private UserDomainService userDomainService;

    /**
     * 生成验证码
     */
    @GetMapping("/codeGenerate")
    public Result codeGenerate(HttpServletRequest request, HttpServletResponse response){
        boolean flag = userDomainService.codeGenerate(request, response);

        // 封装成FrontendResponseResult传给前端
        return new Result(flag ? UserConstant.CODE_GENERATE_OK.getCode() : UserConstant.CODE_GENERATE_ERROR.getCode());
    }

    /**
     * 用户登录
     */
    @GetMapping("/username/{username}/password/{password}")
    public Result login(@PathVariable String username, @PathVariable String password, HttpServletRequest request) {
        // 判断用户是否存在
        boolean flag = userDomainService.login(username, password, request);

        // 封装成FrontendResponseResult传给前端
        return new Result(flag ? UserConstant.LOGIN_OK.getCode() : UserConstant.LOGIN_ERROR.getCode());
    }

    /**
     * 用户注册
     * - 如验证码输入错误等，统一为注册失败
     */
    @PostMapping
    public Result register(@RequestBody Map<String, Object> parameters, HttpServletRequest request) throws IOException {
        // 注册用户
        boolean registerStatus = userDomainService.register(parameters, request);

        // 判断注册的结果，封装成FrontendResponseResult传给前端
        return new Result(registerStatus ? UserConstant.REGISTER_OK.getCode() : UserConstant.REGISTER_ERROR.getCode());
    }

    /**
     * 检查用户名是否存在
     */
    @GetMapping("/{username}")
    public Result selectUser(@PathVariable String username) {
        // 对用户名是否存在进行判断
        boolean flag = userDomainService.checkUserExist(username);

        // 判断用户名是否存在的结果，封装成FrontendResponseResult传给前端
        return new Result(flag ? UserConstant.USERNAME_EXIST_OK.getCode() : UserConstant.USERNAME_EXIST_ERROR.getCode());
    }
}

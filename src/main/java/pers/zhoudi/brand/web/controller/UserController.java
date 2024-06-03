package pers.zhoudi.brand.web.controller;

import pers.zhoudi.brand.constant.UserConstant;
import pers.zhoudi.brand.dto.Result;
import pers.zhoudi.brand.domain.service.UserDomainService;
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
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/codeGenerate")
    public void codeGenerate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userDomainService.codeGenerate(request, response);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @GetMapping("/username/{username}/password/{password}")
    public Result login(@PathVariable String username, @PathVariable String password, HttpServletRequest request){
        // 判断用户是否存在
        boolean flag = userDomainService.loginUser(username, password, request);

        // 封装成FrontendResponseResult传给前端
        return new Result(flag ? UserConstant.LOGIN_OK.getCode() : UserConstant.LOGIN_ERROR.getCode());
    }

    /**
     * 用户注册
     * - 如验证码输入错误等，统一为注册失败
     * @param parameters
     * @param request
     * @return
     */
    @PostMapping
    public Result register(@RequestBody Map<String, Object> parameters, HttpServletRequest request){
        // 注册用户
        boolean registerStatus = userDomainService.registerUser(parameters, request);

        // 判断注册的结果，封装成FrontendResponseResult传给前端
        return new Result(registerStatus ? UserConstant.REGISTER_OK.getCode() : UserConstant.REGISTER_ERROR.getCode());
    }

    /**
     * 检查用户名是否已经存在
     * @param username
     * @return
     */
    @GetMapping("/{username}")
    public Result selectUser(@PathVariable String username) {
        // 对用户名是否存在进行判断
        boolean flag = userDomainService.checkUser(username);

        // 判断用户名是否存在的结果，封装成FrontendResponseResult传给前端
        return new Result(flag ? UserConstant.USERNAME_EXIST.getCode() : UserConstant.USERNAME_NOT_EXIST.getCode());
    }
}

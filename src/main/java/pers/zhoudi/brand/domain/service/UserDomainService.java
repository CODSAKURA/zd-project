package pers.zhoudi.brand.domain.service;

import pers.zhoudi.brand.domain.entity.QUser;
import pers.zhoudi.brand.domain.entity.User;
import pers.zhoudi.brand.util.CodeGenerateUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Map;

/**
 * 服务层：用户类
 *
 * @author : 周迪
 * @date : 2024/04/30
 */
@Repository
@Scope("prototype")
@Transactional
public class UserDomainService {
    @PersistenceContext
    private EntityManager em;

    /**
     * 生成验证码
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public void codeGenerate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 创建Session
        HttpSession session = request.getSession();

        // 生成验证码
        ServletOutputStream os = response.getOutputStream();
        String codeGenerate = CodeGenerateUtils.outputVerifyImage(100, 50, os, 4);

        // 将验证码存在Session中
        session.setAttribute("codeGenerateGen", codeGenerate);
    }

    /**
     * 登录方法（检测用户是否存在）
     * @param username
     * @param password
     * @param request
     * @return
     */
    public boolean loginUser(String username, String password, HttpServletRequest request){
        // 创建Session
        HttpSession session = request.getSession();

        // 查询用户
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QUser qUser = QUser.user;
        BooleanExpression condition = qUser.username.eq(username).and(qUser.password.eq(password));

        // 判断用户是否存在
        User user = queryFactory.selectFrom(qUser).where(condition).fetchOne();

        // 如用户不存在，则返回登陆失败
        if (user == null) {
            return false;
        }

        // 模拟出错
        // int i = 1/0;

        // 如用户存在，将存储记录存到Session中
        session.setAttribute("user", user);

        // 返回结果
        return true;
    }

    /**
     * 注册方法（保存用户信息）
     * @param parameters
     * @param request
     * @return
     */
    public boolean registerUser(Map<String, Object> parameters, HttpServletRequest request) {
        // 获取输入的验证码,用户名和密码
        String code = (String) parameters.get("code");
        String username = (String) parameters.get("username");
        String password = (String) parameters.get("password");

        // 从Session中获取程序生成的验证码
        HttpSession session = request.getSession();
        String codeGenerateGen = (String) session.getAttribute("codeGenerateGen");

        // 如验证码不正确，则注册失败
        if (!codeGenerateGen.equalsIgnoreCase(code)) {
            return false;
        }

        // 验证码正确，开始注册
        // 插入数据
        em.persist(new User(username, password));

        // 模拟出错
        // int i = 1/0;

        // 返回结果
        return true;
    }


    /**
     * 检查用户名是否存在
     * @param username
     * @return
     */
    public boolean checkUser(String username) {
        // 从数据库中获取username所对应的的用户
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QUser qUser = QUser.user;
        BooleanExpression condition = qUser.username.eq(username);

        // 模拟出错
        // int i = 1/0;

        // 判断获取结果，并返回
        return queryFactory.selectFrom(qUser).where(condition).fetchCount() > 0;
    }
}

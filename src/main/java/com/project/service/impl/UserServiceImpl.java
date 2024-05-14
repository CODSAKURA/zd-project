package com.project.service.impl;

import com.project.util.CodeGenerateUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.project.pojo.QUser;
import com.project.pojo.User;
import com.project.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 服务层：用户类
 *
 * @author : 周迪
 * @date : 2024/04/30
 * TODO 检查是否能跟Brand的AOP整合在一起
 */
@Repository
@Scope("prototype")
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean codeGenerate(HttpServletRequest request, HttpServletResponse response) {
        // 创建Session
        HttpSession session = request.getSession();

        try {
            // 生成验证码
            ServletOutputStream os = response.getOutputStream();
            String codeGenerate = CodeGenerateUtils.outputVerifyImage(100, 50, os, 4);

            // 将验证码存在Session中
            session.setAttribute("codeGenerateGen", codeGenerate);

            // 模拟出错
            // int i = 1/0;

            // 返回结果
            return true;
        } catch (Exception e) {
            // 如验证码已存在Session中，则从Session中删除验证码
            if (session.getAttribute("codeGenerateGen") == null) {
                session.removeAttribute("codeGenerateGen");
            }

            // 返回结果
            return false;
        }
    }

    /**
     * 登录方法（检测用户是否存在）
     */
    @Override
    public boolean login(String username, String password, HttpServletRequest request) {
        // 创建Session
        HttpSession session = request.getSession();

        try {
            // 如传入的任意一个的值为null，则报错
            if (username == null || password == null) {
                throw new NullPointerException();
            }

            // 查询用户
            JPAQueryFactory queryFactory = new JPAQueryFactory(em);
            QUser qUser = QUser.user;
            BooleanExpression condition = qUser.username.eq(username).and(qUser.password.eq(password));

            // 判断用户是否存在
            User user = queryFactory.selectFrom(qUser).where(condition).fetchOne();

            // 如用户不存在，则报错
            if (user == null) {
                throw new Exception("User does not exist");
            }

            // 模拟出错
            // int i = 1/0;

            // 如用户存在，将存储记录存到Session中
            session.setAttribute("user", user);

            // 返回结果
            return true;
        } catch (Exception e) {
            // 如查询失败，则执行以下步骤
            // 打印错误信息
            e.printStackTrace();

            // 如用户已存在Session中，则从Session中删除用户
            if(session.getAttribute("user") != null){
                session.removeAttribute("user");
            }

            // 返回结果
            return false;
        }
    }

    /**
     * 注册方法（保存用户信息）
     */
    @Override
    public boolean register(Map<String, Object> parameters, HttpServletRequest request) {
        // 插入行为未执行
        boolean action = false;
        try {
            // 获取输入的验证码
            String code = (String) parameters.get("code");

            // 从Session中获取程序生成的验证码
            HttpSession session = request.getSession();
            String codeGenerateGen = (String) session.getAttribute("codeGenerateGen");

            // 如验证码不正确，则注册失败
            if (!codeGenerateGen.equalsIgnoreCase(code)) {
                return false;
            }

            // 验证码正确，开始注册
            // 判断传入的username以及password是否为null
            String username = (String) parameters.get("username");
            String password = (String) parameters.get("password");

            // 判断入参是否为空
            if (username == null || password == null) {
                throw new NullPointerException();
            }

            // 插入行为开始执行
            action = true;

            // 插入数据
            em.persist(new User(username, password));

            // 模拟出错
            // int i = 1/0;

            // 返回结果
            return true;
        } catch (Exception e) {
            // 如插入失败，则执行以下步骤
            // 打印错误信息
            e.printStackTrace();

            // 删除原本插入的数据
            if (action) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }

            // 返回结果
            return false;
        }
    }


    /**
     * 检查用户名是否存在
     */
    @Override
    public boolean checkUserExist(String username) {
        try {
            // 如传入的值为null，则报错
            if (username == null) {
                throw new NullPointerException();
            }

            // 从数据库中获取username所对应的的用户
            JPAQueryFactory queryFactory = new JPAQueryFactory(em);
            QUser qUser = QUser.user;
            BooleanExpression condition = qUser.username.eq(username);

            // 模拟出错
            // int i = 1/0;

            // 判断获取结果，并返回
            return queryFactory.selectFrom(qUser).where(condition).fetchCount() > 0;
        } catch (Exception e) {
            // 如查询中途出现Exception，则执行以下步骤
            // 打印异常
            e.printStackTrace();

            // 返回结果
            return false;
        }
    }
}

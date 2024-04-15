package com.project.service.impl;

import com.project.config.SpringConfig;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.project.pojo.QUser;
import com.project.pojo.User;
import com.project.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.*;

@Repository
@Scope("prototype")
public class UserServiceImpl implements UserService {
    private static EntityManager em;

    @PostConstruct
    public void init(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        EntityManagerFactory entityManagerFactory = applicationContext.getBean(EntityManagerFactory.class);
        em = entityManagerFactory.createEntityManager();
    }

    /**
     * 登录方法（检测用户是否存在）
     */
    @Override
    public User login(String username, String password) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QUser qUser = QUser.user;
        BooleanExpression condition = qUser.username.eq(username).and(qUser.password.eq(password));
        return queryFactory.selectFrom(qUser).where(condition).fetchOne();
    }

    /**
     * 注册方法（保存用户信息）
     */
    @Override
    public void register(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    /**
     * 检查用户名是否存在
     */
    @Override
    public boolean checkUserExist(String username) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QUser qUser = QUser.user;
        BooleanExpression condition = qUser.username.eq(username);
        return queryFactory.selectFrom(qUser).where(condition).fetchCount() > 0;
    }
}

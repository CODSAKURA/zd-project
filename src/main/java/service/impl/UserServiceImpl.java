package service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import pojo.QUser;
import pojo.User;
import service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

public class UserServiceImpl implements UserService {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static JPAQueryFactory queryFactory;

    static {
        emf = Persistence.createEntityManagerFactory("hibernateJPA");
        em = emf.createEntityManager();
        queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 登录方法（检测用户是否存在）
     */
    @Override
    public User login(String username, String password) {
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
        QUser qUser = QUser.user;
        BooleanExpression condition = qUser.username.eq(username);
        return queryFactory.selectFrom(qUser).where(condition).fetchCount() > 0;
    }
}

package com.itheima.service.impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UserServiceImpl implements UserService {
    //统一：获取SqlSessionFactory对象
    private SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();

    /**
     * 登陆方法
     */
    public User login(String username, String password){
        //获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //获取UserMapper
        UserMapper usermapper = sqlSession.getMapper(UserMapper.class);

        //调用方法
        User user = usermapper.select(username, password);

        //释放资源
        sqlSession.close();

        //返回对象
        return user;
    }

    /**
     * 注册方法
     */
    public void register(User user){
        //获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //获取UserMapper
        UserMapper usermapper = sqlSession.getMapper(UserMapper.class);

        //注册
        usermapper.add(user);

        //提交事务
        sqlSession.commit();

        //释放资源
        sqlSession.close();
    }

    /**
     * 判断用户名是否存在
     */
    public boolean checkUserExist(String username){
        //获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //获取UserMapper
        UserMapper usermapper = sqlSession.getMapper(UserMapper.class);

        //true没有用户可以加，false有用户不可以加
        boolean flag = (usermapper.selectByUsername(username) == null);

        return flag;
    }
}

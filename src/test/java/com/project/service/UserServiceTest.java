package com.project.service;

import com.project.config.SpringConfig;
import com.project.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testRegisterForNull() {
        User user = null;
        userService.register(user);
        int i = 1/0;
    }

    @Test
    public void testLoginForNull() {
        String userName = null;
        String password = null;
        userService.login(userName, password);
        int i = 1/0;
    }
}

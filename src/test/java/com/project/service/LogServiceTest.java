package com.project.service;

import com.project.config.SpringConfig;
import com.project.pojo.Log;
import com.project.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class LogServiceTest {

    @Autowired
    private LogService logService;

    // 测试日志服务
    @Test
    public void testLogService() {
        Log log = new Log();
        log.setLogMethod("register");
        log.setLogParameters("zd , zdzfl001183");
        logService.saveLog(log);
    }
}

package pers.brand.domain.service;

import pers.zhoudi.brand.config.SpringConfig;
import pers.zhoudi.brand.domain.entity.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.zhoudi.brand.domain.service.LogDomainService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class LogServiceTest {

    @Autowired
    private LogDomainService logDomainService;

    // 测试日志服务
    @Test
    public void testLogService() {
        Log log = new Log();
        log.setLogMethod("register");
        log.setLogParameters("zd , zdzfl001183");
        logDomainService.saveLog(log);
    }
}

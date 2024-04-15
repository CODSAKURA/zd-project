package com.project;

import com.project.config.SpringConfig;
import com.project.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IocContainerTest {
    /**
     * 此测试方法从容器中获取所有的bean并打印出来
     */
    @Test
    public void testIocContainer() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        String[] beanCollector = applicationContext.getBeanDefinitionNames();
        for (String bean : beanCollector) {
            System.out.println(bean);
        }
    }

}

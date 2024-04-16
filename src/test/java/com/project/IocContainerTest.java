package com.project;

import com.project.config.SpringConfig;
import com.project.web.serlvet.UserServlet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManagerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class IocContainerTest {

    // 注入ApplicationContext
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 此测试方法从容器中获取所有的bean并打印出来
     */
    @Test
    public void testIocContainer() {
        String[] beanCollector = applicationContext.getBeanDefinitionNames();
        for (String bean : beanCollector) {
            System.out.println(bean);
        }
    }

    /**
     * 此测试方法从容器中获取特定的bean并打印出来
     */
    @Test
    public void testIocContainerForUserServlet() {
        UserServlet userServlet = applicationContext.getBean(UserServlet.class);
        System.out.println(userServlet);
    }

    /**
     * 此测试方法从容器中获取特定的bean并打印出来
     */
    @Test
    public void testIocContainerForEntityManager() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        EntityManagerFactory entityManagerFactory = applicationContext.getBean(EntityManagerFactory.class);
        System.out.println(entityManagerFactory);
    }

}

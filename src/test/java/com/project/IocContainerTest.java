package com.project;

import com.project.config.SpringConfig;
import com.project.web.serlvet.UserServlet;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManagerFactory;

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

    /**
     * 此测试方法从容器中获取特定的bean并打印出来
     */
    @Test
    public void testIocContainerForUserServlet() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
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

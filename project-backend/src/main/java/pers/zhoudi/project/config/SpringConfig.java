package pers.zhoudi.project.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Spring配置类（此类不归Spring别的bean管理，但管理其所对应的bean）
 *
 * @author : 周迪
 * @date : 2024/04/15
 */
// 扫描项目中的所有bean（排除SpringMVC管理的bean）
@ComponentScan(value= "pers.zhoudi.project",
        excludeFilters = {
                // 排除Controller的beans
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        classes = Controller.class
                ),
                // 排除WebConfig的bean
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        classes = Configuration.class
                )
        }
)
@EnableTransactionManagement    // 允许事务管理
@EnableAspectJAutoProxy     // 允许AOP功能
public class SpringConfig {
    // 数据源配置
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/zd_project?currentSchema=public&useSSL=false&useServerPrepStmts=true");
        dataSource.setUsername("postgres");
        dataSource.setPassword("zdzfl001183");
        return dataSource;
    }

    // 实体管理器工厂配置
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("pers.zhoudi.project.domain.entity");
        factory.setDataSource(dataSource());
        return factory;
    }

    // 事务管理器配置
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}

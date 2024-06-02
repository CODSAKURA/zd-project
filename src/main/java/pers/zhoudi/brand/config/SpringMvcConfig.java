package pers.zhoudi.brand.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Spring MVC配置类（此类不归别的Spring bean类管理，但是管理其对应的bean）
 * @author : 周迪
 * @date : 2024/04/27
 */
@ComponentScan({"pers.zhoudi.brand.web.controller","pers.zhoudi.brand.config"})
@EnableWebMvc // 开启解析JSON格式
public class SpringMvcConfig {
}

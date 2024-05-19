package pers.brand.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 此类不归别的Spring bean类管理，但是管理其对应的bean
 */
@ComponentScan({"pers.brand.web.controller","pers.brand.config"})
@EnableWebMvc // 开启解析JSON格式
public class SpringMvcConfig {
}

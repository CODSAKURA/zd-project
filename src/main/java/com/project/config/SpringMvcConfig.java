package com.project.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan({"com.project.controller","com.project.filter"})
@EnableWebMvc // 开启解析JSON格式
public class SpringMvcConfig {
}

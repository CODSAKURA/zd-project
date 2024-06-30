package pers.zhoudi.brand.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC配置类（此类不归别的Spring bean类管理，但是管理其对应的bean）
 *
 * @author : 周迪
 * @date : 2024/04/27
 */
@ComponentScan("pers.zhoudi.brand.web.controller")
@EnableWebMvc // 开启解析JSON格式
public class SpringMvcConfig implements WebMvcConfigurer {
    /**
     * 当访问静态资源时，不要被SpringMVC拦截，直接让Tomcat去读取对应路径下的静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/imgs/**").addResourceLocations("/imgs/");
        registry.addResourceHandler("/element-ui/**").addResourceLocations("/element-ui/");
    }
}

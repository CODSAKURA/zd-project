package pers.zhoudi.brand.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 当访问静态资源时，不要被SpringMVC拦截，直接让Tomcat去读取对应路径下的静态资源
 * @author : 周迪
 * @date : 2024/04/27
 */
@Configuration //程序启动时让SpringMVC加载该类
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/imgs/**").addResourceLocations("/imgs/");
        registry.addResourceHandler("/element-ui/**").addResourceLocations("/element-ui/");
    }
}

package pers.zhoudi.brand.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 使用SpringMVC的DispatcherServlet来处理请求（此类不归Spring管理，也不管理Spring的bean）
 * @author : 周迪
 * @date : 2024/04/27
 */
public class ServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

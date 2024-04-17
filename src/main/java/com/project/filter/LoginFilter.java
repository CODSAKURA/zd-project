package com.project.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录过滤器
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        /*以下代码为如果用户直接访问用户登录页面，相关文件都要全部放行*/
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String[] urls = {"/login.html","/imgs/","/css/","/user/","/register.html","/js/"};

        //判断当前访问的资源路径是否在需要被放行的资源路径
        String url = req.getRequestURL().toString();
        for(String u : urls){
            if (url.contains(u)){
                chain.doFilter(req, response);
                return;
            }
        }

        //------------------------------------------------------------------------

        /*以下代码为如果用户直接访问login之外的页面的话，判断是否登录，如果没登录就强制跳转到登陆页面【放行前的逻辑（判断session中是否有user对象）】*/
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");

        //放不放行判断
        if (user != null){
            //已经登录（放行）
            chain.doFilter(req, response);
        }else{
            //没有登录, 跳转到登录页面
            String contextPath = req.getContextPath();
            res.sendRedirect(contextPath+"/login.html");
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}

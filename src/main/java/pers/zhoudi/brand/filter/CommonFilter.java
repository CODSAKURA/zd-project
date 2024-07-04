package pers.zhoudi.brand.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 综合过滤器
 * 该过滤器处理跨域问题，并对未登录用户尝试访问受保护页面进行拦截。
 * 它向响应中添加必要的头信息以允许跨域请求，并处理预检 (OPTIONS) 请求。
 * 对于尝试访问受保护页面的未登录用户，重定向到登录页面。
 *
 * @author : 周迪
 * @date : 2024/06/30
 */
@WebFilter("/*")
public class CommonFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 将ServletRequest和ServletResponse转换为HttpServletRequest和HttpServletResponse。
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 向响应中添加头信息以允许来自任何域的跨域请求。
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");//允许跨域的请求方式
        response.setHeader("Access-Control-Max-Age", "3600");//预检请求的间隔时间
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Access-Control-Allow-Headers");//允许跨域请求携带的请求头
        response.setHeader("Access-Control-Allow-Credentials","true");//若要返回cookie、携带seesion等信息则将此项设置我true

        // 检查请求方法是否为OPTIONS，即预检请求。
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            // 如果是OPTIONS请求，发送包含“Success”消息的响应。
            servletResponse.getOutputStream().write("Success".getBytes("utf-8"));
            return;
        }

        // 如果用户直接访问用户页面(登录+注册)，相关文件都要全部放行。
        String[] urls = {
                "/css/",
                "/element-ui/",
                "/imgs/",
                "/js/",
                "/login.html",
                "/register.html",
                "/users"
        };
        String url = request.getRequestURL().toString();
        for (String u : urls) {
            if (url.contains(u)) {
                // 如包含,就放行
                filterChain.doFilter(request, servletResponse);
                return;
            }
        }

        // 如果用户尝试访问login之外的页面，需判断是否已经登录(通过Session)
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        // 放不放行判断
        if (user != null) {
            // 已经登录, 放行
            filterChain.doFilter(request, servletResponse);
        } else {
            // 没有登录, 跳转到登录页面
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/pages/login.html");
        }
    }

    /**
     * 该方法在过滤器首次初始化时调用。
     * 目前不需要初始化操作，所以此方法为空。
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * 该方法在过滤器被移除时调用。
     * 目前不需要清理操作，所以此方法为空。
     */
    @Override
    public void destroy() {}
}

package pers.zhoudi.brand.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决跨域问题的过滤器
 * 该过滤器向响应中添加必要的头信息以允许跨域请求。
 * 它还处理预检 (OPTIONS) 请求。
 *
 * 作者: 周迪
 * 日期: 2024/06/17 00:12
 */

@WebFilter(filterName = "requestFilter", urlPatterns = {"/*"})
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 该方法在过滤器首次初始化时调用。
        // 目前不需要初始化操作，所以此方法为空。
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 将ServletRequest和ServletResponse转换为HttpServletRequest和HttpServletResponse。
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 向响应中添加头信息以允许来自任何域的跨域请求。
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Cache-Control,Pragma,Content-Type,Token, Content-Type");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600"); // 预检请求的响应缓存3600秒（1小时）。
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 检查请求方法是否为OPTIONS，即预检请求。
        String method = request.getMethod();
        if (method.equalsIgnoreCase("OPTIONS")) {
            // 如果是OPTIONS请求，发送包含“Success”消息的响应。
            servletResponse.getOutputStream().write("Success".getBytes("utf-8"));
        } else {
            // 对于其他请求方法，将请求传递到过滤链的下一个过滤器。
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        // 该方法在过滤器被移除时调用。
        // 目前不需要清理操作，所以此方法为空。
    }
}

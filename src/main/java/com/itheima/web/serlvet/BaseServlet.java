package com.itheima.web.serlvet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 *  替换HttpServlet，根据请求的最后一段路径来进行方法分发，而不是通过POST或者GET来进行方法分发
 */
public class BaseServlet extends HttpServlet {

    // 根据请求的最后一段路径来进行方法分发，而不是通过POST或者GET来进行方法分发
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求路径
        String uri = req.getRequestURI();

        // 获取最后一段路径（方法名）
        int index = uri.lastIndexOf('/');
        String methodName = uri.substring(index + 1);

        // 执行方法【可直接用this，因为service被哪个子Servlet调用，this就会指定哪个Servlet】

        // 1.先获得当前被调用的Servlet的字节码对象（字节码对象表示了类的所有信息，而实例对象表示了当前实例的信息）
        Class<? extends BaseServlet> cls = this.getClass();

        try {
            // 2.再获取对应的method对象
            Method method = cls.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);// 第一个参数为要获取的方法名，第二三个参数为该方法的两个参数类型

            // 3.最后执行方法
            method.invoke(this, req,resp); //第一个参数是调用该方法的对象，第二三个参数是要传递给那个方法的参数

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

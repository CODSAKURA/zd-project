package com.project.web.serlvet;

import com.alibaba.fastjson.JSON;
import com.project.config.SpringConfig;
import com.project.pojo.Brand;
import com.project.pojo.PageBean;
import com.project.service.BrandService;
import com.project.service.UserService;
import com.project.service.impl.BrandServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet {
    //统一：创建userService对象
    private BrandService brandService;

    /**
     * servlet类是由servlet容器管理，而不是spring容器，所以如果想在自定义servlet类中使用bean来注入类，单纯这样是不行的。
     * 需要重写init()方法，在servlet初始化时给它充填带注解的bean实例。
     */
    @PostConstruct
    public void init(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        brandService = applicationContext.getBean(BrandService.class);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取JSON请求体数据
        BufferedReader reader = request.getReader();
        String params = reader.readLine();

        // 将JSON数据转换成Java对象
        Brand brand = JSON.parseObject(params, Brand.class);

        // 调用方法
        brandService.add(brand);

        // 返回成功数据
        response.getWriter().write("success");
    }


    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取JSON请求体数据
        BufferedReader reader = request.getReader();
        String params = reader.readLine();

        // 将JSON数据转换成Java对象
        Brand brand = JSON.parseObject(params, Brand.class);

        if (brand != null) {
            // 获取当前Brand的id
            Integer brandID = brand.getId();

            // 调用方法
            brandService.delete(brandID);

            //返回成功数据
            response.getWriter().write("success");
        } else {
            // 返回错误数据
            response.getWriter().write("Brand object is null");
        }
    }


    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取JSON请求体数据
        BufferedReader reader = request.getReader();
        String params = reader.readLine();

        // 将JSON数据转换成Java对象
        Brand brand = JSON.parseObject(params, Brand.class);

        // 调用方法
        brandService.update(brand);

        // 返回成功数据
        response.getWriter().write("success");
    }

    public void deleteByIds(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取JSON请求体数据
        BufferedReader reader = request.getReader();
        String params = reader.readLine();

        // 将JSON数据转换成Java对象
        int[] ids = JSON.parseObject(params, int[].class);

        // 调用方法
        brandService.deleteByIds(ids);

        //返回成功数据
        response.getWriter().write("success");
    }


    /**
     * 分页条件查询
     */
    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 接收两个参数（当前页码，每页展示条数）[Get]
        Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));

        // 2. 获取请求体参数 [Post]
        BufferedReader reader = request.getReader();
        String params = reader.readLine();

        // 将JSON数据转换成Java对象
        Brand brand = JSON.parseObject(params, Brand.class);

        // 调用方法
        PageBean<Brand> pageBean = brandService.selectByPageAndCondition(currentPage, pageSize, brand);

        // 将数据转换成JSON数据
        String jsonString = JSON.toJSONString(pageBean);

        // 将数据存储在response，并发送
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
}


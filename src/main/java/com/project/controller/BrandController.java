package com.project.controller;

import com.project.pojo.Brand;
import com.project.pojo.PageBean;
import com.project.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/brand")// TODO 需要分析接口的合理性
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 分页查询
     */
    @RequestMapping("/selectByPageAndCondition")
    @ResponseBody
    public PageBean<Brand> selectByPageAndCondition(Integer currentPage, Integer pageSize, @RequestBody Brand brand) {
        // 调用方法
        PageBean<Brand> pageBean = brandService.selectByPageAndCondition(currentPage, pageSize, brand);

        // 向前端返回数据
        return pageBean;
    }

    /**
     * 添加品牌
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add(@RequestBody Brand brand) {
        // 调用方法
        brandService.add(brand);

        // 返回成功数据
        return "success";
    }

    /**
     * 更新品牌
     */
    @RequestMapping("/update")
    @ResponseBody
    public String update(@RequestBody Brand brand) {
        // 调用方法
        brandService.update(brand);

        // 返回成功数据
        return "success";
    }

    /**
     * 删除特定品牌
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody Brand brand) {
        // 如Brand为空，则返回错误信息
        if (brand == null) {
            return "Brand object is null";
        }

        // 获取当前Brand的id
        Integer brandID = brand.getId();

        // 调用方法
        brandService.delete(brandID);

        //返回成功数据
        return "success";
    }

    /**
     * 批量删除品牌
     */
    @RequestMapping("/deleteByIds")
    @ResponseBody
    public String deleteByIds(@RequestBody int[] ids){
        // 调用方法
        brandService.deleteByIds(ids);

        //返回成功数据
        return "success";
    }
}

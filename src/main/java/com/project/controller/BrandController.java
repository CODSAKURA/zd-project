package com.project.controller;

import com.project.pojo.Brand;
import com.project.pojo.PageBean;
import com.project.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 控制层：品牌类（Rest风格）
 * @author : 周迪
 * @date : 2024/04/28
 */
@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌
     */
    @GetMapping("/pages/{currentPage}/pageSize/{pageSize}/brand")
    public PageBean<Brand> selectByPageAndCondition(@PathVariable Integer currentPage,
                                                    @PathVariable Integer pageSize, Brand brand) {
        // 调用方法
        PageBean<Brand> pageBean = brandService.selectByPageAndCondition(currentPage, pageSize, brand);

        // 返回数据
        return pageBean;
    }

    /**
     * 添加品牌
     */
    @PostMapping
    public String add(@RequestBody Brand brand) {
        // 调用方法
        brandService.add(brand);

        // 返回成功数据
        return "success";
    }

    /**
     * 更新品牌
     * FIXME 添加Apifox接口测试
     */
    @PostMapping("/update")
    public String update(@RequestBody Brand brand) {
        // 调用方法
        brandService.update(brand);

        // 返回成功数据
        return "success";
    }

    /**
     * 删除特定品牌
     */
    @PostMapping("/delete")
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
    @PostMapping("/deleteByIds")
    public String deleteByIds(@RequestBody int[] ids){
        // 调用方法
        brandService.deleteByIds(ids);

        //返回成功数据
        return "success";
    }
}

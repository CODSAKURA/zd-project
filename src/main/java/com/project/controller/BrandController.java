package com.project.controller;

import com.project.enums.BrandResponseCode;
import com.project.frontend.FrontendResponseResult;
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
     * 添加品牌
     */
    @PostMapping
    public FrontendResponseResult add(@RequestBody Brand brand) {
        // 调用方法
        boolean flag = brandService.addBrand(brand);

        // 封装成FrontendResponseResult返回数据
        return new FrontendResponseResult(flag ? BrandResponseCode.ADD_OK.getCode() : BrandResponseCode.ADD_ERROR.getCode());
    }

    /**
     * 更新品牌
     */
    @PostMapping("/update")
    public FrontendResponseResult update(@RequestBody Brand brand) {
        // 调用方法
        boolean flag = brandService.updateBrand(brand);

        // 封装成FrontendResponseResult返回数据
        return new FrontendResponseResult(flag ? BrandResponseCode.UPDATE_OK.getCode() : BrandResponseCode.UPDATE_ERROR.getCode());
    }

    /**
     * 删除特定品牌
     */
    @PostMapping("/delete")
    public FrontendResponseResult delete(@RequestBody Brand brand) {
        // 调用方法
        boolean flag = brandService.deleteBrand(brand);

        //封装成FrontendResponseResult返回数据
        return new FrontendResponseResult(flag ? BrandResponseCode.DELETE_OK.getCode() : BrandResponseCode.DELETE_ERROR.getCode());
    }

    /**
     * 批量删除品牌
     */
    @PostMapping("/deleteBatch")
    public FrontendResponseResult deleteBatch(@RequestBody Brand[] brands){
        // 调用方法
        boolean flag = brandService.deleteBatchBrands(brands);

        //封装成FrontendResponseResult返回数据
        return new FrontendResponseResult(flag ? BrandResponseCode.DELETE_OK.getCode() : BrandResponseCode.DELETE_ERROR.getCode());
    }

    /**
     * 分页查询品牌
     */
    @GetMapping("/pages/{currentPage}/pageSize/{pageSize}/brand")
    public FrontendResponseResult selectByPageAndCondition(@PathVariable Integer currentPage,
                                                           @PathVariable Integer pageSize, Brand brand) {
        // 调用方法
        PageBean<Brand> pageBean = brandService.selectBrandByPageAndCondition(currentPage, pageSize, brand);

        // 判断返回的数据是否为null
        boolean flag = pageBean != null;

        // 封装成FrontendResponseResult返回数据
        return new FrontendResponseResult(pageBean, flag ? BrandResponseCode.SELECT_OK.getCode() : BrandResponseCode.SELECT_ERROR.getCode());
    }
}

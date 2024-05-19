package pers.brand.web.controller;

import pers.brand.constant.BrandConstant;
import pers.brand.dto.Result;
import pers.brand.domain.entity.Brand;
import pers.brand.domain.entity.PageBean;
import pers.brand.domain.service.BrandDomainService;
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
    private BrandDomainService brandDomainService;

    /**
     * 添加品牌
     */
    @PostMapping
    public Result add(@RequestBody Brand brand) {
        // 调用方法
        boolean flag = brandDomainService.addBrand(brand);

        // 封装成FrontendResponseResult返回数据
        return new Result(flag ? BrandConstant.ADD_OK.getCode() : BrandConstant.ADD_ERROR.getCode());
    }

    /**
     * 更新品牌
     */
    @PostMapping("/update")
    public Result update(@RequestBody Brand brand) {
        // 调用方法
        boolean flag = brandDomainService.updateBrand(brand);

        // 封装成FrontendResponseResult返回数据
        return new Result(flag ? BrandConstant.UPDATE_OK.getCode() : BrandConstant.UPDATE_ERROR.getCode());
    }

    /**
     * 删除特定品牌
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody Brand brand) {
        // 调用方法
        boolean flag = brandDomainService.deleteBrand(brand);

        //封装成FrontendResponseResult返回数据
        return new Result(flag ? BrandConstant.DELETE_OK.getCode() : BrandConstant.DELETE_ERROR.getCode());
    }

    /**
     * 批量删除品牌
     */
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Brand[] brands){
        // 调用方法
        boolean flag = brandDomainService.deleteBatchBrands(brands);

        //封装成FrontendResponseResult返回数据
        return new Result(flag ? BrandConstant.DELETE_OK.getCode() : BrandConstant.DELETE_ERROR.getCode());
    }

    /**
     * 分页查询品牌
     */
    @GetMapping("/pages/{currentPage}/pageSize/{pageSize}/brand")
    public Result selectByPageAndCondition(@PathVariable Integer currentPage,
                                           @PathVariable Integer pageSize, Brand brand) {
        // 调用方法
        PageBean<Brand> pageBean = brandDomainService.selectBrandByPageAndCondition(currentPage, pageSize, brand);

        // 判断返回的数据是否为null
        boolean flag = pageBean != null;

        // 封装成FrontendResponseResult返回数据
        return new Result(pageBean, flag ? BrandConstant.SELECT_OK.getCode() : BrandConstant.SELECT_ERROR.getCode());
    }
}

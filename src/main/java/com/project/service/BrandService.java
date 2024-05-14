package com.project.service;

import com.project.pojo.Brand;
import com.project.pojo.PageBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BrandService {

    /**
     * 增添数据
     *
     * @return
     */
    boolean add(Brand brand);

    /**
     * 删除数据
     */
    boolean delete(Brand brand);

    /**
     * 更新数据
     */
    boolean update(Brand brand);

    /**
     * 批量删除
     */
    boolean deleteBatch(Brand[] brands);

    /**
     * 分页条件查询【currentPage（当前页码），pageSize（每页展示条数）,brand（查询的条件）】
     */
    PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize, Brand brand);
}

package com.project.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.project.pojo.Brand;
import com.project.pojo.PageBean;
import com.project.pojo.QBrand;
import com.project.service.BrandService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 服务层：品牌类
 * @author : 周迪
 * @date : 2024/04/30
 */
@Repository
@Scope("prototype")
public class BrandServiceImpl implements BrandService {
    @PersistenceContext
    private EntityManager em;

    /**
     * 增加品牌
     */
    @Override
    public void add(Brand brand) {
        em.persist(brand);
    }

    /**
     * 删除单个特定品牌
     */
    @Override
    public void delete(int id) {
        Brand brand = em.find(Brand.class, id);
        em.remove(brand);
    }

    /**
     * 更新特定品牌
     */
    @Override
    public void update(Brand brand) {
        em.merge(brand);
    }

    /**
     * 删除一系列特定id的品牌
     */
    @Override
    public void deleteByIds(int[] ids) {
        for (int id : ids) {
            delete(id);
        }
    }

    /**
     * 分页条件查询品牌
     */
    @Override
    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize, Brand brand) {
        // 创建查询工厂
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // 创建查询对象
        QBrand qBrand = QBrand.brand;

        // 添加查询条件
        BooleanExpression expression = qBrand.isNotNull().or(qBrand.isNotNull());
        if (brand != null) {
            if (brand.getBrandName() != null && !"".equals(brand.getBrandName())) {
                expression = expression.and(qBrand.brandName.like("%" + brand.getBrandName() + "%"));
            }
            if (brand.getCompanyName() != null && !"".equals(brand.getCompanyName())) {
                expression = expression.and(qBrand.companyName.like("%" + brand.getCompanyName() + "%"));
            }
            if(brand.getStatus() != null && !"".equals(brand.getStatus())){
                expression = expression.and(qBrand.status.eq(brand.getStatus()));
            }
        }

        // 计算查询的总记录数
        long totalCount = queryFactory.selectFrom(qBrand)
                .where(expression)
                .fetchCount();

        // 分页查询的起始页
        int start = (currentPage - 1) * pageSize;

        // 分页查询的结果
        List<Brand> brands = queryFactory.selectFrom(qBrand)
                .where(expression)
                .orderBy(qBrand.id.desc())
                .offset(start)
                .limit(pageSize)
                .fetch();

        // 封装PageBean对象
        PageBean<Brand> pageBean = new PageBean<>();
        pageBean.setTotalCount((int) totalCount);
        pageBean.setRow(brands);

        return pageBean;
    }

}

package com.project.service.impl;

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
     * 删除特定id的品牌
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
     * 删除特定id的品牌
     */
    @Override
    public void deleteByIds(int[] ids) {
        for (int id : ids) {
            delete(id);
        }
    }

    /**
     * 分页条件查询【currentPage（当前页码），pageSize（每页展示条数）,brand（查询的条件）】
     */
    @Override
    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize, Brand brand) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QBrand qBrand = QBrand.brand;
        long totalCount = queryFactory.selectFrom(qBrand)
                .where(qBrand.brandName.like("%" + brand.getBrandName() + "%")).fetchCount();

        int start = (currentPage - 1) * pageSize;
        List<Brand> brands = queryFactory.selectFrom(qBrand)
                .where(qBrand.brandName.like("%" + brand.getBrandName() + "%"))
                .orderBy(qBrand.id.desc())
                .offset(start)
                .limit(pageSize)
                .fetch();

        PageBean<Brand> pageBean = new PageBean<>();
        pageBean.setTotalCount((int) totalCount); // 将totalCount类型转换为int（假设总数不会超过Integer的最大值）
        pageBean.setRow(brands);

        return pageBean;
    }
}

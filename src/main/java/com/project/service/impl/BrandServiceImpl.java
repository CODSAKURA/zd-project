package com.project.service.impl;

import com.project.config.SpringConfig;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.project.pojo.Brand;
import com.project.pojo.PageBean;
import com.project.pojo.QBrand;
import com.project.service.BrandService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
@Scope("prototype")
public class BrandServiceImpl implements BrandService {
    private static EntityManager em;

    @PostConstruct
    public void init(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        EntityManagerFactory entityManagerFactory = applicationContext.getBean(EntityManagerFactory.class);
        em = entityManagerFactory.createEntityManager();
    }


    @Override
    public void add(Brand brand) {
        em.getTransaction().begin();
        em.persist(brand);
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        Brand brand = em.find(Brand.class, id);
        em.getTransaction().begin();
        em.remove(brand);
        em.getTransaction().commit();
    }

    @Override
    public void update(Brand brand) {
        em.getTransaction().begin();
        em.merge(brand);
        em.getTransaction().commit();
    }

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

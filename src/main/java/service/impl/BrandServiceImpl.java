package service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import pojo.Brand;
import pojo.PageBean;
import pojo.QBrand;
import service.BrandService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class BrandServiceImpl implements BrandService {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static JPAQueryFactory queryFactory;

    static {
        emf = Persistence.createEntityManagerFactory("hibernateJPA");
        em = emf.createEntityManager();
        queryFactory = new JPAQueryFactory(em);
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

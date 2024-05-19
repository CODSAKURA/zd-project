package pers.brand.domain.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import pers.brand.domain.entity.Brand;
import pers.brand.domain.entity.PageBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import pers.brand.domain.entity.QBrand;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 服务层：品牌类
 *
 * @author : 周迪
 * @date : 2024/04/30
 * TODO 项目层级差一层（4）
 */
@Service
@Scope("prototype")
@Transactional
public class BrandDomainService {
    @PersistenceContext
    private EntityManager em;

    /**
     * 增加品牌
     * - 如brand为null
     * - 如brand里面的任意属性为null，则抛出异常【除去属性id】
     */
    public boolean addBrand(Brand brand) {
        // 插入数据
        em.persist(brand);

        // 模拟出错
        // int i = 1/0;

        // 返回结果
        return true;
    }

    /**
     * 更新特定品牌
     * - 如brand为null
     * - 如brand里面的任意属性为null，则抛出异常
     */
    public boolean updateBrand(Brand brand) {
        // 更新数据
        em.merge(brand);

        // 模拟出错
        // int i = 1/0;

        // 返回结果
        return true;
    }


    /**
     * 删除特定品牌
     * - 如brand为null
     * - 如brand里面的任意属性为null，则抛出异常
     */
    public boolean deleteBrand(Brand brand){

        // 删除所找到的brand
        em.remove(brand);

        // 模拟出错
        // int i = 1/0;

        // 返回成功的数据
        return true;
    }


    /**
     * 批量删除品牌
     * - 如传入的要删除的品牌长度为0，那么返回false
     * TODO 调用delete方法时，并未调用delete的AOP功能, 如删除未存在的数据会导致服务内部报错（3）
     */
    public boolean deleteBatchBrands(Brand[] brands){
        // 如没有需要删除的，则有问题，直接返回false
        if (brands.length == 0) {
            return false;
        }

        // 批量删除
        for (Brand brand : brands) {
            // 只要执行一个删除失败了就回退之前的所有步骤
            boolean flag = deleteBrand(brand);
            if (!flag) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }
        return true;
    }

    /**
     * 分页条件查询品牌
     * - 如传入的currentPage，pageSize以及brand都不是合理的值，则抛出异常
     * - 如传入的brand里的所有属性都是null，则抛出异常。
     */
    public PageBean<Brand> selectBrandByPageAndCondition(int currentPage, int pageSize, Brand brand) {
        PageBean<Brand> pageBean = new PageBean<>();
        try {
            // 判断传入的brand以及其内部参数是否为null
            // 如brand对象为null，则抛出异常
            if (currentPage <= 0 || pageSize < 0 || brand == null) {
                throw new Exception("Parameters are invalid");
            }

            // 如brand内部参数为null，则抛出异常
            if (brand.getId() == null
                    && brand.getBrandName() == null
                    && brand.getCompanyName() == null
                    && brand.getDescription() == null
                    && brand.getOrdered() == null
                    && brand.getStatus() == null) {
                throw new Exception("Brand object contains all null properties");
            }

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
                if (brand.getStatus() != null && !"".equals(brand.getStatus())) {
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
            pageBean.setTotalCount((int) totalCount);
            pageBean.setRow(brands);

            // 模拟出错
            // int i = 1/0;

            // 返回数据
            return pageBean;
        } catch (Exception e) {
            // 如查询失败，则执行以下步骤
            e.printStackTrace(); // 打印问题
            pageBean = null; // 返回一个null的对象
            return pageBean;
        }
    }
}

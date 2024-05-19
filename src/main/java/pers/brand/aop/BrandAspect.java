package pers.brand.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import pers.brand.domain.entity.Brand;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Aspect
public class BrandAspect {

    @PersistenceContext
    private EntityManager em;

    // 切入点定义：捕获BrandDomainService中的所有以Brand结尾的方法
    @Pointcut("execution(* pers.brand.domain.service.BrandDomainService.*Brand(..))")
    private void commonForBrandMethod() {
    }

    // 环绕通知
    @Around("commonForBrandMethod()")
    public Object parameterCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 获取方法的参数
            Object[] args = joinPoint.getArgs();

            // 提取第一个参数
            Brand brand = (Brand) args[0];

            // 如入参为null，则报错
            if (brand == null) {
                throw new Exception("Brand object is null");
            }

            // 如brand内部参数为null，则抛出异常
            if (brand.getBrandName() == null
                    || brand.getCompanyName() == null
                    || brand.getDescription() == null
                    || brand.getOrdered() == null
                    || brand.getStatus() == null) {
                throw new Exception("Brand object contains null properties");
            }

            // add方法除外，需要执行以下的步骤
            if(!"addBrand".equals(joinPoint.getSignature().getName())) {
                // 判断id是否为null
                if(brand.getId() == null){
                    throw new Exception("Brand object contains null properties");
                }

                // 通过传入的brand参数的id找到数据库里的brand
                Brand result = em.find(Brand.class, brand.getId());

                // 如查不到数据，则抛出异常
                if (result == null) {
                    throw new Exception("Brand object not found");
                }

                // delete操作需判断要删除的和数据库里查到的值是否一致，如一直则需删除查到数据库里的数据
                if("deleteBrand".equals(joinPoint.getSignature().getName())){
                    if (!result.getId().equals(brand.getId())
                            || !result.getBrandName().equals(brand.getBrandName())
                            || !result.getCompanyName().equals(brand.getCompanyName())
                            || !result.getDescription().equals(brand.getDescription())
                            || result.getOrdered() != brand.getOrdered()
                            || result.getStatus() != brand.getStatus()) {
                        throw new Exception("Brand properties do not match");
                    }

                    // 将查询到的数据作为参数传给delete
                    args[0] = result;
                }
            }

            // 执行方法
            boolean output = (boolean) joinPoint.proceed(args);

            // 模拟出错
            // int i = 1/0;

            // 返回结果
            return output;
        } catch (Exception e) {
            // 如行为执行失败，则执行以下步骤
            // 打印问题
            e.printStackTrace();

            // 回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            // 返回结果
            return false;
        }
    }
}

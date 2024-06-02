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
import java.lang.reflect.Array;
import java.util.*;

/**
 * 此类为AOP类，提供入参进行过滤给Domain层的所有方法。
 * @author : 周迪
 * @date : 2024/05/20
 */
@Component
@Aspect
public class ServiceAspect {

    @PersistenceContext
    private EntityManager em;

    @Pointcut("execution(* pers.brand.domain.service.BrandDomainService.*Brand(..)) || " +
            "execution(* pers.brand.domain.service.UserDomainService.*User(..))")
    private void commonForServiceMethod() {
    }

    // 对传入到User以及Brand的Domain层方法里的入参进行统一过滤
    @Around("commonForServiceMethod()")
    public Object parameterCheckForNull(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 获取当前方法所在的完整的类名
            String classNameComplete = joinPoint.getTarget().getClass().getName();

            // 获取类名
            int lastDotIndex = classNameComplete.lastIndexOf('.');
            String className = classNameComplete.substring(lastDotIndex + 1);

            // 对参数进行过滤
            // 判断类名是否为Brand开头
            Object[] args = null;
            if (className.startsWith("Brand")) {
                args = brandParameterCheckForNull(joinPoint);
            }

            // 判断类名是否为User开头
            if (className.startsWith("User")) {
                args = userParameterCheckForNull(joinPoint);
            }

            // 判断后的参数还有没有漏过滤的
            if (args == null) {
                throw new NullPointerException();
            }

            // 传入参数并执行方法
            boolean output = (boolean) joinPoint.proceed(args);

            // 模拟出错
            // int i = 1/0;

            // 返回结果
            return output;
        } catch (Exception e) {
            // 如行为执行失败，统一执行以下步骤
            // 1. 打印问题
            e.printStackTrace();

            // 2. 回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            // 3. 返回错误
            return false;
        }
    }

    /**
     * 单独对调用User类的方法的参数进行过滤
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public Object[] userParameterCheckForNull(ProceedingJoinPoint joinPoint) throws Exception {
        // 获取所有入参
        Object[] args = joinPoint.getArgs();

        // 统一判断第一个参数是否为null
        if (args[0] == null) {
            throw new NullPointerException();
        }

        // register方法特殊的入参进行判断（第一个参数长度必须等于3，并且里面的每一项都不能是null）
        if ("register".equals(joinPoint.getSignature().getName())) {
            // 获取第一个参数
            Map<String, Object> parameters = (Map<String, Object>) args[0];

            // 判断第一个参数内是否包含code，username以及password三个参数
            if (parameters.size() != 3) {
                throw new Exception("Lack Parameters");
            }

            // 判断三个参数任意一项是否为空
            if (parameters.get(0) == null || parameters.get(1) == null || parameters.get(2) == null) {
                throw new NullPointerException();
            }
        }

        // login方法特殊的入参判断（由于有两个入参，需判断第二个入参是否为空）
        if ("login".equals(joinPoint.getSignature().getName())) {
            if (args[1] == null) {
                throw new NullPointerException();
            }
        }

        // 返回过滤后的参数
        return args;
    }

    /**
     * 单独对调用Brand类的方法的参数进行过滤
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public Object[] brandParameterCheckForNull(ProceedingJoinPoint joinPoint) throws Exception {
        // 获取所有参数
        Object[] args = joinPoint.getArgs();

        // 封装第一个参数成ArrayList
        ArrayList<Brand> brands = null;
        if(args[0] instanceof Brand[]) {
            // 判断要批量删除的数据是否存在（针对于批量删除）
            if(((Brand[]) args[0]).length == 0){
                throw new Exception("Empty brand list");
            }

            // 封装成ArrayList
            brands = new ArrayList<>(Arrays.asList((Brand[]) args[0]));

        }else {
            Brand brand = (Brand) args[0];
            brands.add(brand);
        }

        // 判断第一个参数里的每个元素是否为null
        for (Brand brand : brands) {
            // 判断元素里的（除了id外）每个属性是否为null（针对所有方法）
            if (Objects.isNull(brand.getBrandName())
                    || Objects.isNull(brand.getCompanyName())
                    || Objects.isNull(brand.getDescription())
                    || Objects.isNull(brand.getOrdered())
                    || Objects.isNull(brand.getStatus())) {
                throw new Exception("Brand object contains null properties");
            }

            // 除了add方法外，需做以下额外的判断
            if (!"addBrand".equals(joinPoint.getSignature().getName())) {
                // id属性是否为null
                if (Objects.isNull(brand.getId())) {
                    throw new Exception("Brand object contains null properties");
                }

                // 如不为null，则通过id查找数据库的真实brand
                Brand result = em.find(Brand.class, brand.getId());

                // 如查不到数据，则抛出异常
                if (Objects.isNull(result)) {
                    throw new Exception("Brand object does not exist");
                }

                // 如查到数据，delete还需与查到的数据进行比对，判断是否为要删除的数据
                if ("deleteBrand".equals(joinPoint.getSignature().getName())) {
                    if (!result.getId().equals(brand.getId())
                            || !result.getBrandName().equals(brand.getBrandName())
                            || !result.getCompanyName().equals(brand.getCompanyName())
                            || !result.getDescription().equals(brand.getDescription())
                            || result.getOrdered() != brand.getOrdered()
                            || result.getStatus() != brand.getStatus()) {
                        throw new Exception("Brand properties do not match");
                    }
                }
            }
        }

        // 返回过滤后的参数
        return args;
    }
}

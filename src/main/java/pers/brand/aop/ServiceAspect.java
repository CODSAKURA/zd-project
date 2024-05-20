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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 此类为AOP类，用于将Domain层方法相同的部分进行整合，目前主要是用于对入参进行过滤。
 * @author : 周迪
 * @date : 2024/05/20
 */
@Component
@Aspect
public class ServiceAspect {

    @PersistenceContext
    private EntityManager em;

    @Pointcut("execution(* pers.brand.domain.service.BrandDomainService.*Brand(..)) || " +
            "execution(* pers.brand.domain.service.UserDomainService.*(..))")
    private void commonForServiceMethod() {
    }

    // 对传入到User以及Brand的Domain层方法里的入参进行统一过滤
    @Around("commonForServiceMethod()")
    public Object parameterCheck(ProceedingJoinPoint joinPoint) throws Throwable {
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
                args = brandParameterCheck(joinPoint);
            }

            // 判断类名是否为User开头
            if (className.startsWith("User")) {
                args = userParameterCheck(joinPoint);
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

            // 2. 删除存储在Session中的值
            // 动态地获取哪个参数是HttpServletRequest类型
            for (Object arg : joinPoint.getArgs()) {
                if (arg instanceof HttpServletRequest) {
                    // 通过request获取Session
                    HttpServletRequest request = (HttpServletRequest) arg;
                    HttpSession session = request.getSession();

                    // 如验证码已存在Session中，则从Session中删除验证码
                    if (session.getAttribute("codeGenerateGen") == null) {
                        session.removeAttribute("codeGenerateGen");
                    }

                    // 如有用户记录存入到Session中，则删除存储在Session中的用户
                    if (session.getAttribute("user") != null) {
                        session.removeAttribute("user");
                    }

                    // 跳出循环
                    break;
                }
            }

            // 3. 回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            // 4. 返回错误
            return false;
        }
    }

    /**
     * 单独对调用User类的方法的参数进行过滤
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public Object[] userParameterCheck(ProceedingJoinPoint joinPoint) throws Exception {
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
    public Object[] brandParameterCheck(ProceedingJoinPoint joinPoint) throws Exception {
        // 获取所有参数
        Object[] args = joinPoint.getArgs();

        // 获取第一个参数
        Brand brand = (Brand) args[0];

        // 如第一个入参为null，则报错
        if (brand == null) {
            throw new Exception("Brand object is null");
        }

        // 如第一个参数的内部任意一个参数为null，则报错
        if (brand.getBrandName() == null
                || brand.getCompanyName() == null
                || brand.getDescription() == null
                || brand.getOrdered() == null
                || brand.getStatus() == null) {
            throw new Exception("Brand object contains null properties");
        }

        // delete和update方法需做以下额外的步骤
        if (!"addBrand".equals(joinPoint.getSignature().getName())) {
            // 判断内部参数id是否为null
            if (brand.getId() == null) {
                throw new Exception("Brand object contains null properties");
            }

            // 如不为null，则通过id查找数据库的真实brand
            Brand result = em.find(Brand.class, brand.getId());

            // 如查不到数据，则抛出异常
            if (result == null) {
                throw new Exception("Brand object not found");
            }

            // 如查到数据，delete还需对查到的数据和入参进行比对，判断是否为要删除的数据
            if ("deleteBrand".equals(joinPoint.getSignature().getName())) {
                if (!result.getId().equals(brand.getId())
                        || !result.getBrandName().equals(brand.getBrandName())
                        || !result.getCompanyName().equals(brand.getCompanyName())
                        || !result.getDescription().equals(brand.getDescription())
                        || result.getOrdered() != brand.getOrdered()
                        || result.getStatus() != brand.getStatus()) {
                    throw new Exception("Brand properties do not match");
                }

                // 如没有任何问题，则将查询到的数据作为参数进行传递
                args[0] = result;
            }
        }

        // 返回过滤后的参数
        return args;
    }
}

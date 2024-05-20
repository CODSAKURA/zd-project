package pers.brand.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.brand.domain.entity.Log;
import pers.brand.domain.service.LogDomainService;

/**
 * 此类为AOP类，提供额外的记录日志功能给Domain层的所有方法。
 * @author : 周迪
 * @date : 2024/05/19
 */
@Component
@Aspect
public class LoggerAspect {

    @Autowired
    private LogDomainService logDomainService;

    // 对于 com.project.service 包下面的所有类的所有方法进行切面【除去LogService】
    @Pointcut("execution(* pers.brand.domain.service.*.*(..)) && !execution(* pers.brand.domain.service.LogDomainService.*(..))")
    private void methodPointcut() {}

    // 不管方法运行成功与否，都要把日志记录下来为了方便排查和查看
    @Around("methodPointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            logInsert(pjp);
        }
        return result;
    }

    /**
     * 封装成Log对象，并存储在数据库的Log表中
     * @param pjp
     */
    private void logInsert(ProceedingJoinPoint pjp) {
        // 获取方法名
        Signature signature = pjp.getSignature();
        String methodName = signature.getName();

        // 获取方法的所有参数
        Object[] args = pjp.getArgs();
        StringBuffer parameters = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                args[i] = "null";
            }
            parameters.append(args[i] + ", ");
        }
        // 删除末尾的逗号
        String subParameters = parameters.substring(0, parameters.lastIndexOf(","));

        // 封装成Log对象
        Log log = new Log();
        log.setLogMethod(methodName);
        log.setLogParameters(subParameters);

        //保存日志
        logDomainService.saveLog(log);
    }
}

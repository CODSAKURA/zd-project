package pers.brand.domain.service;

import pers.brand.domain.entity.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;

/**
 * 服务层：记录类
 * @author : 周迪
 * @date : 2024/04/30
 */
@Repository
@Scope("prototype")
@Transactional(propagation = Propagation.REQUIRES_NEW) // 开启事务但是事务不归Spring管理
public class LogDomainService {
    @PersistenceContext
    private EntityManager em;

    /**
     * 将记录保存至Log数据库
     * @param log
     */
    public void saveLog(Log log) {
        // 设置日志时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.setLogTime(sdf.format(System.currentTimeMillis()));

        // 保存日志
        em.persist(log);
    }
}

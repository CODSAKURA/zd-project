package com.project.service.impl;

import com.project.pojo.Log;
import com.project.service.LogService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

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
public class LogServiceImpl implements LogService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveLog(Log log) {
        // 设置日志时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.setLogTime(sdf.format(System.currentTimeMillis()));

        // 保存日志
        em.persist(log);
    }
}

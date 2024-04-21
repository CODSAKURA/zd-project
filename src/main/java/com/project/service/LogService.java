package com.project.service;

import com.project.pojo.Log;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface LogService {

    // 插入日志
    void saveLog(Log log);
}

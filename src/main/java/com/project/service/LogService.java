package com.project.service;

import com.project.pojo.Log;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LogService {

    // 插入日志
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void saveLog(Log log);
}

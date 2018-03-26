package com.ring.core.service.impl.sys;

import com.ring.api.example.SysLogExample;
import com.ring.api.model.sys.SysLog;
import com.ring.core.mapper.sys.SysLogExMapper;
import com.ring.core.service.AbstractService;
import com.ring.core.service.sys.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CHAO 2018-03-10 14:43
 */
@Service
public class SysLogServiceImpl extends AbstractService<SysLog, SysLogExample> implements SysLogService {
    @Autowired
    private SysLogExMapper mapper;

    @Override
    public SysLogExMapper getMapper() {
        return mapper;
    }
}
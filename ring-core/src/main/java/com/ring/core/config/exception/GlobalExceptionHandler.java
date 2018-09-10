package com.ring.core.config.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.ring.api.constant.ResultEnum;
import com.ring.api.util.Result;
import com.ring.common.exception.ArgumentException;
import com.ring.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/2/9
 * @author CHAO 修改日期：2018/2/9
 * @version 1.0.0
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Log LOG = LogFactory.get();

    @ExceptionHandler(value = ArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handle(ArgumentException e) {
        LOG.error(e, "参数异常");
        return Result.create(ResultEnum.LACK_PARAM.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handle(BusinessException e) {
        LOG.error(e, "业务异常");
        return Result.create(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handle(Exception e) {
        LOG.error(e, "未知异常");
        return Result.create(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "服务内部错误");
    }
}

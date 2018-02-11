package com.ring.common.util;

import cn.hutool.core.util.StrUtil;
import com.ring.api.constant.ResultEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/2/11
 * @author CHAO 修改日期：2018/2/11
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Assert {
    public static <T> void isNull(T object, String argument) throws NullPointerException {
        if (object == null) {
            throw new BusinessException(ResultEnum.LACK_PARAM.getCode(), StrUtil.format("[Assertion failed] - this argument [{}] is required; it must not be null", argument));
        }
    }

    public static void isAnyNull(String argument, Object... property) throws NullPointerException {
        String[] params = argument.split(",");
        if (params.length != property.length) {
            throw new RuntimeException("[Assertion failed] - The length of the parameter and the length of the object are not equal");
        }
        for (int i = 0; i < params.length; i++) {
            isNull(property[i], params[i]);
        }
    }

    public static void isEmpty(String text, String argument) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            throw new BusinessException(ResultEnum.LACK_PARAM.getCode(), StrUtil.format("[Assertion failed] - this String argument [{}]  must have length; it must not be null or empty", argument));
        }
    }

    public static void isAnyEmpty(String argument, String... property) throws NullPointerException {
        String[] params = argument.split(",");
        if (params.length != property.length) {
            throw new RuntimeException("[Assertion failed] - The length of the parameter and the length of the object are not equal");
        }
        for (int i = 0; i < params.length; i++) {
            isEmpty(property[i], params[i]);
        }
    }
}

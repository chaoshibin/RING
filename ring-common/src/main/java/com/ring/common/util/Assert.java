package com.ring.common.util;

import cn.hutool.core.util.StrUtil;
import com.ring.common.exception.ArgumentException;
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
            throw new ArgumentException(StrUtil.format("[Assertion failed] - this argument [{}] is required; it must not be null", argument));
        }
    }

    public static void isEmpty(String text, String argument) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            throw new ArgumentException(StrUtil.format("[Assertion failed] - this String argument [{}]  must have length; it must not be null or empty", argument));
        }
    }
}

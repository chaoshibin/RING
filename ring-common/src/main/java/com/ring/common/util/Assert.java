package com.ring.common.util;

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

    public static <T> void isNull(T object, String argument) {
        if (object == null) {
            throw new ArgumentException(argument);
        }
        if (object instanceof String) {
            if (StringUtils.isBlank(String.valueOf(object))) {
                throw new ArgumentException(argument);
            }
        }
    }
}

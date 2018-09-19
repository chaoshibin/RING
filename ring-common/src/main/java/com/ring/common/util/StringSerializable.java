package com.ring.common.util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * 功能描述:
 * <p/>
 *
 * @author chaoshibin 新增日期：2018/9/19
 * @author chaoshibin 修改日期：2018/9/19
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class StringSerializable implements Serializable {


    @Override
    protected Object clone() {
        return SerializationUtils.clone(this);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).toString();
    }


}

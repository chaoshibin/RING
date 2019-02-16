package com.ring.core.util;

/**
 * Copyright (C), 2019-2019, 深圳市xxx科技有限公司
 *
 * @author: chaoshibin
 * Date:     2019/2/16 9:27
 * Description:
 */
public enum SerialTypeEnum {
    ORDER(0);

    SerialTypeEnum(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }
}

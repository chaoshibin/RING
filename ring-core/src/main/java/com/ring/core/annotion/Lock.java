package com.ring.core.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/6/14
 * @author CHAO 修改日期：2018/6/14
 * @version 1.0.0
 * @since 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {

    String prefix();

    String unique() default "";
}

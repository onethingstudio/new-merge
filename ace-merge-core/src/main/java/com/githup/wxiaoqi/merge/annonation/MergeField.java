package com.githup.wxiaoqi.merge.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * @author ace
 * @create 2018/2/1.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
public @interface MergeField {
    /**
     * 查询值
     * @return
     */
    String key() default "";

    /**
     * 目标类
     * @return
     */
    Class<? extends Object> service() default Object.class;

    /**
     * 调用方法
     * @return
     */
    String method() default "";

    /**
     * 请求类型 可以以List.class 组装请求，也可以以默认String逗号分隔请求
     * @return
     */
    Class requestType() default List.class;
}

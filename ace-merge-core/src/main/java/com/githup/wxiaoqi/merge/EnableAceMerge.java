package com.githup.wxiaoqi.merge;

import com.githup.wxiaoqi.merge.configuration.MergeAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author ace
 * @create 2018/2/3.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({MergeAutoConfiguration.class})
public @interface EnableAceMerge {
}

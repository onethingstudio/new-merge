package com.githup.wxiaoqi.merge.configuration;

import com.githup.wxiaoqi.merge.core.BeanFactoryUtils;
import com.githup.wxiaoqi.merge.core.MergeCore;
import com.githup.wxiaoqi.merge.facade.DefaultMergeResultParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ace
 * @create 2018/2/3.
 */
@Configuration
@ComponentScan("com.githup.wxiaoqi.merge.aspect")
public class MergeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BeanFactoryUtils beanFactoryUtils() {
        return new BeanFactoryUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public MergeCore mergeCore() {
        return new MergeCore();
    }

    @Bean
    public DefaultMergeResultParser defaultMergeResultParser() {
        return new DefaultMergeResultParser();
    }
}

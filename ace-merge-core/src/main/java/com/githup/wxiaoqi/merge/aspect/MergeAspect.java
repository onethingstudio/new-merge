package com.githup.wxiaoqi.merge.aspect;

import com.githup.wxiaoqi.merge.annonation.MergeResult;
import com.githup.wxiaoqi.merge.core.MergeCore;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author ace
 * @create 2018/2/2.
 */
@Aspect
@Component
public class MergeAspect {
    @Autowired
    private MergeCore mergeCore;


    @Pointcut("@annotation(com.githup.wxiaoqi.merge.annonation.MergeResult)")
    public void methodPointcut() {
    }


    @Around("methodPointcut()&&@annotation(anno)")
    public Object interceptor(ProceedingJoinPoint pjp, MergeResult anno) throws Throwable {
        try {
            return mergeCore.mergeData(pjp,anno);
        }catch(Exception e){
            return pjp.proceed();
        }
    }
}

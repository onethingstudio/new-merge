package com.githup.wxiaoqi.merge.core;

import com.githup.wxiaoqi.merge.annonation.MergeField;
import com.githup.wxiaoqi.merge.annonation.MergeResult;
import com.githup.wxiaoqi.merge.facade.DefaultMergeResultParser;
import com.githup.wxiaoqi.merge.facade.IMergeResultParser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author ace
 * @create 2018/2/2.
 */
@Slf4j
public class MergeCore {

//    public MergeCore(MergeProperties mergeProperties) {
//
//    }
    /**
     * aop方式加工
     *
     * @param pjp
     * @param anno
     * @return
     * @throws Throwable
     */
    public Object mergeData(ProceedingJoinPoint pjp, MergeResult anno) throws Throwable {
        Object proceed = pjp.proceed();
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method m = signature.getMethod();
            // 获取当前方法的返回值
            Type rawType = null;
            Class clazz = null;
            try {
                ParameterizedType parameterizedType = (ParameterizedType) m.getGenericReturnType();

                rawType = parameterizedType.getRawType();
                Type[] types = parameterizedType.getActualTypeArguments();
                clazz = ((Class) types[0]);
                List<?> result = null;

                // 非list直接返回
                if (anno.resultParser().equals(DefaultMergeResultParser.class) && ((Class) rawType).isAssignableFrom(List.class)) {
                    result = (List<?>) proceed;
                    mergeResult(clazz, result);
                    return result;
                } else {
                    IMergeResultParser bean = BeanFactoryUtils.getBean(anno.resultParser());
                    result = bean.parser(proceed);
                    mergeResult(clazz, result);
                    return proceed;
                }
            }catch (Exception e){
                log.error("某属性数据聚合失败", e);
            }
            // 获取当前方法的返回值
            Type parameterizedType =  m.getGenericReturnType();
            rawType =m.getReturnType();
            clazz =m.getReturnType();
            Object result = proceed;
            mergeOne(clazz, result);
            return proceed;
        } catch (Exception e) {
            log.error("某属性数据聚合失败", e);
            return proceed;
        }

    }

    /**
     * 手动调用进行配置合并
     *
     * @param clazz
     * @param result
     * @throws ExecutionException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void mergeResult(Class clazz, List<?> result) throws ExecutionException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> mergeFields = new ArrayList<Field>();
        Map<String, Map<Object, Object>> invokes = new HashMap<>();
        String className = clazz.getName();
        Map<String,Field> fieldMaps= Arrays.asList(fields).stream().collect(Collectors.toMap(Field::getName, e -> e));
        Map<String, MergeField> mergeFieldMaps=new HashMap<String, MergeField>();
        // 获取属性
        for (Field field : fields) {
            MergeField annotation = field.getAnnotation(MergeField.class);
            if (annotation != null) {
                Object args = annotation.key();
                mergeFieldMaps.put(field.getName(),annotation);
                mergeFields.add(field);

                StringBuffer sb = new StringBuffer("");
                List<Object> ids = new ArrayList<>();
                result.stream().forEach(obj -> {
                    Field f=fieldMaps.get(annotation.key());
                    f.setAccessible(true);
                    Object o = null;
                    try {
                        o = f.get(obj);
                        if (o != null) {
                            if (!ids.contains(o)) {
                                ids.add(o);
                                sb.append(o.toString()).append(",");
                            }
                        }
                    } catch (IllegalAccessException e) {
                        log.error("数据属性加工失败:" + field, e);
                        throw new RuntimeException("数据属性加工失败:" + field, e);
                    }

                });

                if(!annotation.requestType().isAssignableFrom(List.class)){
                    args = sb.substring(0, sb.length() - 1);
                }else{
                    if(ids.size()>0) {
                        args = ids;
                    }else{
                        args=null;
                    }
                }
                if(args!=null) {
                    Object bean = BeanFactoryUtils.getBean(annotation.service());
                    Method method = annotation.service().getMethod(annotation.method(), annotation.requestType());
                    Map<Object, Object> value = (Map<Object, Object>) method.invoke(bean, args);
                    invokes.put(field.getName(), value);
                }
            }
        }
        result.stream().forEach(obj -> {
            mergeObjFieldValue(obj, mergeFields, invokes,fieldMaps,mergeFieldMaps);
        });
    }

    /**
     * 手动对单个结果进行配置合并
     *
     * @param clazz
     * @param mergeObj
     * @throws ExecutionException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void mergeOne(Class clazz, Object mergeObj) throws ExecutionException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> mergeFields = new ArrayList<Field>();
        Map<String, Map<Object, Object>> invokes = new HashMap<>();
        String className = clazz.getName();
        Map<String,Field> fieldMaps= Arrays.asList(fields).stream().collect(Collectors.toMap(Field::getName, e -> e));
        Map<String, MergeField> mergeFieldMaps=new HashMap<String, MergeField>();
        // 获取属性
        for (Field field : fields) {
            MergeField annotation = field.getAnnotation(MergeField.class);
            if (annotation != null) {
                mergeFieldMaps.put(field.getName(),annotation);
                mergeFields.add(field);
                Object args = annotation.key();
                List<Object> ids = new ArrayList<>();
                Field f=fieldMaps.get(annotation.key());
                f.setAccessible(true);
                Object o = null;
                try {
                    o = f.get(mergeObj);
                } catch (IllegalAccessException e) {
                    log.error("数据属性加工失败:" + field, e);
                    throw new RuntimeException("数据属性加工失败:" + field, e);
                }
                if (o != null) {
                    if(!annotation.requestType().isAssignableFrom(List.class)) {
                        args = o.toString();
                    }else{
                        ids.add(o);
                        if(ids.size()>0) {
                            args = ids;
                        }else{
                            args=null;
                        }
                    }
                }
                if(args!=null) {
                    Object bean = BeanFactoryUtils.getBean(annotation.service());
                    Method method = annotation.service().getMethod(annotation.method(), annotation.requestType());
                    Map<Object, Object> value = (Map<Object, Object>) method.invoke(bean, args);
                    invokes.put(field.getName(), value);
                }
            }
        }
        mergeObjFieldValue(mergeObj, mergeFields, invokes,fieldMaps,mergeFieldMaps);
    }

    /**
     * 合并对象属性值
     * @param mergeObj
     * @param mergeFields
     * @param invokes
     */
    private void mergeObjFieldValue(Object mergeObj, List<Field> mergeFields, Map<String, Map<Object, Object>> invokes,Map<String,Field> fieldMaps,Map<String, MergeField> mergeFieldMaps) {
        for (Field field : mergeFields) {
            field.setAccessible(true);
            Object o = null;
            try {
                o = field.get(mergeObj);
                MergeField annotation=mergeFieldMaps.get(field.getName());
                Field f=fieldMaps.get(annotation.key());
                Object value=f.get(mergeObj);
                if(value!=null) {
                    String valueStr = value.toString();
                    if (invokes.get(field.getName()).containsKey(valueStr)) {
                        field.set(mergeObj, invokes.get(field.getName()).get(valueStr));
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("数据属性加工失败:" + field, e);
                throw new RuntimeException("数据属性加工失败:" + field, e);
            }
        }
    }
}

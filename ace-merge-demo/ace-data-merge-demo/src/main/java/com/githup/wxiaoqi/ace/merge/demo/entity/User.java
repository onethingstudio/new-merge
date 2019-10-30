package com.githup.wxiaoqi.ace.merge.demo.entity;

import com.githup.wxiaoqi.ace.merge.demo.biz.UserBiz;
import com.githup.wxiaoqi.ace.merge.demo.feign.IDataFeign;
import com.githup.wxiaoqi.merge.annonation.MergeField;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author ace
 * @create 2018/2/1.
 */
@Data
public class User {
    private String name;

    private String sex;

    //@MergeField(key= "city",feign = IDataFeign.class,method = "getCities",isValueNeedMerge = true)
    private String city;
    @MergeField(key= "sex", service = IDataFeign.class,method = "getGenders")
//    @MergeField(key= "sex", service = UserBiz.class,method = "getGenders")
    private Object sexObj=new String();


    public User(String name, String sex, String city) {
        this.name = name;
        this.sex = sex;
        this.city = city;
    }


}

package com.githup.wxiaoqi.ace.merge.provider.rest;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ace
 * @create 2017/11/20.
 */
@RestController
@RequestMapping("data")
public class DataRest {

    private Logger logger = LoggerFactory.getLogger(DataRest.class);
    @RequestMapping("s")
    public Map<String, Object> getGender(@RequestParam("userId")String userId) {
//        if ("sex_key".equals(key)) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map map1 = new HashMap();
        map1.put("id",1);
        map1.put("name","男");

        map.put("man", map1);
        map1.put("id",2);
        map1.put("name","女");
        map.put("woman",map1);
        return map;
//        } else {
//            return new HashMap<String, String>();
//        }
    }
    @RequestMapping("sex")
    public Map<String, Object> getGenders(@RequestParam("userIdList")List<String> userIdList) {
//        if ("sex_key".equals(key)) {
            Map<String, Object> map = new HashMap<String, Object>();
            Map map1 = new HashMap();
            map1.put("id",1);
            map1.put("name","男");
            map.put("man", map1);
            Map map2 = new HashMap();
            map2.put("id",2);
            map2.put("name","女");
            map.put("woman",map2);
            return map;
//        } else {
//            return new HashMap<String, String>();
//        }
    }

    @RequestMapping("city")
    public Map<String, String> getCities(String ids) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "广州");
        map.put("2", "武汉");
        return map;
    }
}

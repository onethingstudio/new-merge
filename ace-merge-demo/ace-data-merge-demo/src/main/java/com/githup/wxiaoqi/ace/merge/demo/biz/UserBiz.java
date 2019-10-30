package com.githup.wxiaoqi.ace.merge.demo.biz;

import com.githup.wxiaoqi.ace.merge.demo.entity.User;
import com.githup.wxiaoqi.merge.annonation.MergeResult;
import com.githup.wxiaoqi.merge.core.MergeCore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ace
 * @create 2018/2/1.
 */
@Service
@Slf4j
public class UserBiz {
    @Autowired
    private MergeCore mergeCore;

    /**
     * aop注解的聚合方式
     * 其中聚合的方法返回值必须为list,
     * 如果为复杂对象,则需要自定义自己的聚合解析器(实现接口IMergeResultParser)
     */
    @MergeResult
    public List<User> getAotoMergeUser() {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 100; i > 0; i--) {
            users.add(new User("zhangsan" + i, "man", "1"));
            users.add(new User("lisi" + i, "woman", "2"));
            users.add(new User("wangwu" + i, "unkonwn", "2"));
        }
        return users;
    }

    public List<User> getUser() {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 1000; i > 0; i--) {
            users.add(new User("zhangsan" + i, "man", "1"));
            users.add(new User("lisi" + i, "woman", "2"));
            users.add(new User("wangwu" + i, "unkonwn", "2"));
        }
        return users;
    }

    /**
     * 手动聚合方式
     *
     * @return
     */
    public List<User> getMergeUser() {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 1000; i > 0; i--) {
            users.add(new User("zhangsan" + i, "man", "1"));
            users.add(new User("lisi" + i, "woman", "2"));
            users.add(new User("wangwu" + i, "unkonwn", "2"));
        }
        try {
            // list 聚合
            mergeCore.mergeResult(User.class, users);
            // 单个对象聚合
//            mergeCore.mergeOne(User.class,users.get(0));
        } catch (Exception e) {
            log.error("数据聚合失败", e);
        } finally {
            return users;
        }
    }
    @MergeResult
    public User getMergeU() {
        User u= new User("zhangsan1" , "man", "1");
         return u;
    }

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
}

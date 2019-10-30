package com.githup.wxiaoqi.ace.merge.demo.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ace
 * @create 2017/11/20.
 */
@FeignClient("ace-data-provider")
public interface IDataFeign {
    @RequestMapping("data/sex")
    public Map<String, Object> getGenders(@RequestParam("userIdList") List<String> userIdList);
    @RequestMapping("data/s")
    public Map<String, String> getGender(@RequestParam("userId") String userId);
    @RequestMapping("data/city")
    public Map<String, String> getCities(@RequestParam("ids") String ids);
}

package com.githup.wxiaoqi.ace.merge.demo.merge;

import com.githup.wxiaoqi.merge.facade.IMergeResultParser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ace
 * @create 2018/2/3.
 */
@Component
public class TestMergeResultParser implements IMergeResultParser {
    @Override
    public List parser(Object o) {
        return (List)o;
    }
}

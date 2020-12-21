package com.xiaojie.mybatisplus;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.xiaojie.core.dao.DataOperation;
import com.xiaojie.core.dao.Param;
import com.xiaojie.mybatisplus.dao.QueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 数据操作
 *
 * @author wangye
 * @classname DataOperationImpl
 * @date 2020/12/21 10:16
 **/
@Component
public class DataOperationImpl implements DataOperation {
    @Autowired
    private QueryMapper queryMapper;

    public List<Map> selectData(String tableName, String fields, String paramName, Object paramValue) {
        Map paramMap = Maps.newHashMap();
        if (paramValue instanceof Collection) {
            paramMap.put("listKey", paramName);
            paramMap.put("listValue", paramValue);
        } else {
            paramMap.put(paramName, paramValue);
        }
        return queryMapper.selectByMaps(tableName, fields, paramMap);
    }

    public List<Map> selectData(String tableName, String fields, List<Param> params) {
        Map paramMap = Maps.newHashMap();
        for (Param param : params) {
            if (param.getValue() instanceof Collection) {
                paramMap.put("listKey", param.getName());
                paramMap.put("listValue", param.getValue());
            } else {
                paramMap.put(param.getName(), param.getValue());
            }
        }
        return queryMapper.selectByMaps(tableName, fields, paramMap);
    }

    public int delete(String tableName, String paramName, Object paramValue) {
        BaseMapper baseMapper = SpringUtil.getBean(tableName + "Mapper");
        return baseMapper.deleteByMap(ImmutableMap.of(paramName,paramValue));
    }

    public int delete(String tableName, List<Param> params) {
        BaseMapper baseMapper = SpringUtil.getBean(tableName + "Mapper");
        Map paramMap = Maps.newHashMap();
        for (Param param : params) {
            paramMap.put(param.getName(),param.getValue());
        }
        return baseMapper.deleteByMap(paramMap);
    }
}

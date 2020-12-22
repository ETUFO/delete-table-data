package com.xiaojie.mybatisplus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.xiaojie.core.dao.DataOperation;
import com.xiaojie.core.dao.Param;
import com.xiaojie.core.toolkits.CustSpringContextUtil;
import com.xiaojie.mybatisplus.dao.QueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据操作
 * @author wangye
 * @classname DataOperationImpl
 * @date 2020/12/21 10:16
 **/
@Component
public class DataOperationImpl implements DataOperation {

    @Autowired
    private QueryMapper queryMapper;

    @Override
    public List<Map> selectData(String tableName, String fields, String paramName, Object paramValue) {
        Map paramMap = ImmutableMap.of(paramName, paramValue);
        return queryMapper.selectByMaps(tableName, fields, paramMap);
    }

    @Override
    public List<Map> selectData(String tableName, String fields, List<Param> params) {
        Map<String, Object> paramMap = params.stream().collect(Collectors.toMap(Param::getName, Param::getValue));
        return queryMapper.selectByMaps(tableName, fields, paramMap);
    }

    @Override
    public List<Map> selectAll(String tableName, String fields) {
        return queryMapper.selectAll(tableName,fields);
    }

    @Override
    public int delete(String tableName, String paramName, Object paramValue) {
        BaseMapper baseMapper = CustSpringContextUtil.getBean(tableName + "Mapper");
        return baseMapper.deleteByMap(ImmutableMap.of(paramName, paramValue));
    }

    @Override
    public int delete(String tableName, List<Param> params) {
        BaseMapper baseMapper = CustSpringContextUtil.getBean(tableName + "Mapper");
        Map<String, Object> paramMap = params.stream().collect(Collectors.toMap(Param::getName, Param::getValue));
        return baseMapper.deleteByMap(paramMap);
    }
}

package com.xiaojie.core.service;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.xiaojie.autoconfigure.DeleteTableDataProperties;
import com.xiaojie.core.cache.Cache;
import com.xiaojie.core.parse.RemoveExamDataXmlParse;
import com.xiaojie.core.parse.model.delete.RemoveDataTable;
import com.xiaojie.core.parse.model.delete.RemoveDataTables;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 生成删除据所需要的的参数
 *
 * @author wangye
 * @classname GenerateDeleteParam
 * @date 2020/12/16 14:10
 **/
@Component
public class GenerateDeleteParam {

    @Autowired
    private DeleteTableDataProperties deleteTableDataProperties;
    @Autowired
    private Cache defualtCache;

    public Map<String, List<Object>> createDeleteParam(String fileName, Map<String, Object> param) {
        //获取删除表模型
        RemoveDataTables removeDataTables = getRemoveDataTables(fileName);
        List<RemoveDataTable> tableList = removeDataTables.getRemoveDataTableList();
        //整合数据表之间依赖关系
        Map<String, List<RemoveDataTable>> dependTableMap = tableList.stream().filter(table -> {
            return table.getDependTable() != null;
        }).collect(Collectors.groupingBy(table -> {
            return table.getDependTable().getTableName();
        }));
        //获取要删除的数据
//        Map<String, List<Map>> removeDataMap = getRemoveData(examId, tableList, dependTableMap);
        return null;
    }

    private RemoveDataTables getRemoveDataTables(String fileName) {
        String cacheName = deleteTableDataProperties.getCache();
        Cache cache = StringUtils.isBlank(cacheName) ? defualtCache : SpringUtil.getBean(cacheName);
        String json = cache.get(fileName);
        RemoveDataTables removeDataTables = null;
        if (StringUtils.isNotBlank(json)) {
            removeDataTables = JSON.parseObject(json, RemoveDataTables.class);
        } else {
            removeDataTables = RemoveExamDataXmlParse.getRemoveDataTables(fileName);
        }
        if(removeDataTables == null){
            throw new RuntimeException("获取配置文件错误");
        }
        return removeDataTables;
    }

}

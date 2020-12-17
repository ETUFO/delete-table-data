package com.xiaojie.core.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaojie.autoconfigure.DeleteTableDataProperties;
import com.xiaojie.core.cache.Cache;
import com.xiaojie.core.dao.DataBaseOperation;
import com.xiaojie.core.parse.RemoveExamDataXmlParse;
import com.xiaojie.core.parse.model.DependTable;
import com.xiaojie.core.parse.model.DependTables;
import com.xiaojie.core.parse.model.RemoveDataTable;
import com.xiaojie.core.parse.model.RemoveDataTables;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 生成删除据所需要的的参数
 *
 * @author wangye
 * @classname GenerateDeleteParam
 * @date 2020/12/16 14:10
 **/
@Component
public class GenerateDeleteParam {

    private final static int MAX_COUNT = 30000;

    @Autowired
    private DeleteTableDataProperties deleteTableDataProperties;
    @Autowired
    private Cache defualtCache;
    @Autowired
    private DataBaseOperation dataBaseOperation;

    public Map<String, List<Object>> createDeleteParam(String fileName, Map<String, Object> param) {
        //获取删除表模型
        RemoveDataTables removeDataTables = getRemoveDataTables(fileName);
        List<RemoveDataTable> tableList = removeDataTables.getRemoveDataTableList();
        //整合数据表之间依赖关系
        Map<String, List<RemoveDataTable>> dependTableMap = Maps.newHashMap();
        for (RemoveDataTable removeDataTable : tableList) {
            List<DependTable> dependTableList = Optional.ofNullable(removeDataTable.getQueryDependTables())
                    .map(dependTables -> dependTables.getDependTableList()).orElse(new ArrayList<>());
            dependTableList.addAll(Optional.ofNullable(removeDataTable.getDeleteDependTables())
                    .map(dependTables -> dependTables.getDependTableList()).orElse(new ArrayList<>()));
            for (DependTable dependTable : dependTableList) {
                List<RemoveDataTable> list = dependTableMap.get(dependTable.getTableName());
                if (list == null) {
                    list = Lists.newArrayList();
                    dependTableMap.put(dependTable.getTableName(), list);
                }
                list.add(removeDataTable);
            }
        }
        //获取要删除的数据
        Map<String, List<Map>> removeDataMap = getRemoveData(param, tableList, dependTableMap);
        //删除数据

        return null;
    }


    private Map<String, List<Map>> getRemoveData(Map param, List<RemoveDataTable> tableList, Map<String, List<RemoveDataTable>> dependTableMap) {
//        //TODO 将数据放入ThreadLocal中
//        //获取要删除的数据
//        Map<String, List<Map>> removeDataMap = Maps.newHashMap();
//        //避免死循环
//        int count = -1;
//        while (removeDataMap.size() < tableList.size() && count != removeDataMap.size()) {
//            count = removeDataMap.size();
//            for (RemoveDataTable table : tableList) {
//                if (removeDataMap.containsKey(table.getTableName())) continue;
//                boolean flag = Optional.of(table.getQueryParams()).map(queryParams -> queryParams.getParamList() != null).orElse(false);
//                //不依赖其他表,根据参数进行查询数据
//                if (flag) {
//                    List<RemoveDataTable> dependTableList = dependTableMap.get(table.getTableName());
//                    //判断当前表是否被依赖
//                    if (CollectionUtil.isEmpty(dependTableList)) {
//                        List<Map> list = dataBaseOperation.selectData(table.getTableName(), "id", StrUtil.toUnderlineCase(table.getFieldName()), param.get(table.getQueryParamName()));
//                        removeDataMap.put(table.getTableName(), list);
//                    } else {
//                        Set<String> fieldSet = getDependFields(table.getTableName(), dependTableList);
//                        if (CollectionUtil.isEmpty(fieldSet)) {
//                            String error = String.format("配置文件中缺少depend-field-name依赖字段属性");
//                            throw new RuntimeException(error);
//                        } else {
//                            List<Map> list = dataBaseOperation.selectData(table.getTableName(),
//                                    CollectionUtil.join(fieldSet, ","), StrUtil.toUnderlineCase(table.getFieldName()), param.get(table.getQueryParamName()));
//                            removeDataMap.put(table.getTableName(), list);
//                        }
//                    }
//                } else {
//                    //根据配置通过依赖表中字段作为查询条件
//                    List<DependTable> dependTableList1 = getDependTableList(table);
//                    for (DependTable dependTable : dependTableList1) {
//                        removeDataMap.containsKey(dependTable.getTableName());
//                    }
//                    if (!removeDataMap.containsKey(table.getDependTable().getTableName())) {
//                        continue;
//                    }
//                    //判断当前表是否被依赖，生成要查询的字段
//                    List<RemoveDataTable> dependTableList = dependTableMap.get(table.getTableName());
//                    Set<String> fiedSet = CollectionUtil.isNotEmpty(dependTableList) ? getDependFields(dependTableList) : Stream.of("id").collect(Collectors.toSet());
//                    //获取当前表查询条件值
//                    List<Map> list = removeDataMap.get(table.getDependTable().getTableName());
//                    List<Object> paramValueList = list == null ? null : list.stream().map(map -> map.get(table.getDependTable().getDependFieldName())).collect(Collectors.toList());
//                    if (CollectionUtil.isNotEmpty(paramValueList)) {
//                        //查询条件超过MAX_COUNT分多次查询
//                        int size = paramValueList.size();
//                        List<Map> dataList = Lists.newArrayList();
//                        for (int i = 0; i < size; i += MAX_COUNT) {
//                            List<Object> subParamList = paramValueList.subList(i, i + MAX_COUNT > size ? size : i + MAX_COUNT);
//                            dataList.addAll(dataBaseOperation.selectData(table.getTableName(),
//                                    CollectionUtil.join(fiedSet, ","), StrUtil.toUnderlineCase(table.getDependTable().getSourceFieldName()), subParamList));
//                            removeDataMap.put(table.getTableName(), dataList);
//                        }
//                    } else {
//                        removeDataMap.put(table.getTableName(), null);
//                    }
//                }
//            }
//        }
//        return removeDataMap;
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
        if (removeDataTables == null) {
            throw new RuntimeException("获取配置文件错误");
        }
        return removeDataTables;
    }


    private Set<String> getDependFields(String tableName, List<RemoveDataTable> dependTableList) {
//        dependTableList.stream().map(removeTable -> {
//            List<DependTable> list = Optional.ofNullable(removeTable.getQueryDependTables())
//                    .map(dependTables -> Optional.ofNullable(dependTables.getDependTableList())
//                            .map(dependTableList1->dependTableList1).orElse(Lists.newArrayList())).orElse(Lists.newArrayList());
//            list.addAll(Optional.ofNullable(removeTable.getDeleteDependTables())
//                    .map(dependTables -> Optional.ofNullable(dependTables.getDependTableList())
//                            .map(dependTableList1->dependTableList1).orElse(Lists.newArrayList())).orElse(Lists.newArrayList()));
//
//            return list;
//        }).filter(list->{
//           for (DependTable dependTable : list) {
//
//           }
//        }).collect(Collectors.toSet());
//        fieldSet.add("id");
//        return fieldSet;
        return null;
    }

    private List<DependTable> getDependTableList(RemoveDataTable table){
        List<DependTable> list = Lists.newArrayList();
        List<DependTable> list2 = Lists.newArrayList();
        DependTables queryDependTables = table.getQueryDependTables();
        list = queryDependTables == null ? list :
                     Optional.ofNullable(queryDependTables.getDependTableList()).map(dependList->dependList).orElse(Lists.newArrayList());
        DependTables deleteDependTables = table.getDeleteDependTables();
        list2 = deleteDependTables == null ? list2 :
                Optional.ofNullable(deleteDependTables.getDependTableList()).map(dependList->dependList).orElse(Lists.newArrayList());
        list.addAll(list2);
        return list;
    }
}

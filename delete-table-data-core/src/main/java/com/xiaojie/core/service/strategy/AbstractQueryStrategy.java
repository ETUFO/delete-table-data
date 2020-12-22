package com.xiaojie.core.service.strategy;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xiaojie.core.dao.Param;
import com.xiaojie.core.parse.model.*;
import com.xiaojie.core.service.QueryStrategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 查询抽象类
 *
 * @author wangye
 * @classname AbstractQueryStrategy
 * @date 2020/12/18 14:49
 **/
public abstract class AbstractQueryStrategy implements QueryStrategy {


    protected List<Param> getParams(Map param, List<QueryParam> paramList) {
        List<Param> queryParamList = Lists.newArrayList();
        for (QueryParam queryParam : paramList) {
            Param param1 = new Param();
            param1.setName(queryParam.getFieldName());
            param1.setValue(param.get(queryParam.getParamName()));
            queryParamList.add(param1);
        }
        return queryParamList;
    }

    protected List<Param> getParams(Map<String, List<Map>> removeDataMap, RemoveDataTable table) {
        List<Param> paramList = Lists.newArrayList();
        List<DependTable> dependTableList = Optional.ofNullable(table.getQueryDependTables()).map(dependTables -> dependTables.getDependTableList()).orElse(null);
        if (dependTableList == null) {
            return null;
        }
        for (DependTable dependTable : dependTableList) {
            Param param = new Param();
            param.setName(StrUtil.toUnderlineCase(dependTable.getSourceFieldName()));
            List<Map> dataList = removeDataMap.get(dependTable.getTableName());
            List<Object> paramValuse = Optional.ofNullable(dataList).map(list -> {
                return list.stream().map(map -> map.get(dependTable.getDependFieldName())).collect(Collectors.toList());
            }).orElse(null);
            param.setValue(paramValuse);
            paramList.add(param);
        }
        return paramList;
    }


    protected String getQueryFields(RemoveDataTable table, List<RemoveDataTable> dataTableList) {
        Set<String> fieldSet = Sets.newHashSet();
        if (dataTableList == null) {
            fieldSet.add("id");
        } else {
            fieldSet = getDependFields(table.getTableName(), dataTableList);
        }
        return CollectionUtil.join(fieldSet, ",");
    }

    protected Set<String> getDependFields(String tableName, List<RemoveDataTable> dependTableList) {
        Set<String> fieldSet = Sets.newHashSet();
        for (RemoveDataTable table : dependTableList) {
            List<DependTable> queryDependTables = Optional.ofNullable(table.getQueryDependTables()).map(tables -> tables.getDependTableList()).orElse(null);
            if (queryDependTables != null) {
                for (DependTable queryDependTable : queryDependTables) {
                    if (tableName.equals(queryDependTable.getTableName())) {
                        String dependFieldName = queryDependTable.getDependFieldName();
                        String field = dependFieldName.contains("_") ? StrUtil.toUnderlineCase(dependFieldName) + " " + dependFieldName : dependFieldName;
                        fieldSet.add(field);
                    }
                }
            }
            List<DependTable> deleteDependTables = Optional.ofNullable(table.getDeleteDependTables()).map(tables -> tables.getDependTableList()).orElse(null);
            if (deleteDependTables != null) {
                for (DependTable deleteDependTable : deleteDependTables) {
                    if (tableName.equals(deleteDependTable.getTableName())) {
                        String dependFieldName = deleteDependTable.getDependFieldName();
                        String field = dependFieldName.contains("_") ? StrUtil.toUnderlineCase(dependFieldName) + " " + dependFieldName : dependFieldName;
                        fieldSet.add(field);
                    }
                }
            }
        }
        return fieldSet;
    }

    protected List<DependTable> getDependTableList(RemoveDataTable table) {
        List<DependTable> list = Lists.newArrayList();
        List<DependTable> list2 = Lists.newArrayList();
        DependTables queryDependTables = table.getQueryDependTables();
        list = queryDependTables == null ? list :
                Optional.ofNullable(queryDependTables.getDependTableList()).map(dependList -> dependList).orElse(Lists.newArrayList());
        DependTables deleteDependTables = table.getDeleteDependTables();
        list2 = deleteDependTables == null ? list2 :
                Optional.ofNullable(deleteDependTables.getDependTableList()).map(dependList -> dependList).orElse(Lists.newArrayList());
        list.addAll(list2);
        return list;
    }
}

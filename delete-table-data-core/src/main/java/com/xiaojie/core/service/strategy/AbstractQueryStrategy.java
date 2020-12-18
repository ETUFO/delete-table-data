package com.xiaojie.core.service.strategy;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xiaojie.core.cache.Cache;
import com.xiaojie.core.dao.Param;
import com.xiaojie.core.parse.RemoveExamDataXmlParse;
import com.xiaojie.core.parse.model.*;
import com.xiaojie.core.service.QueryStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 查询抽象类
 * @author wangye
 * @classname AbstractQueryStrategy
 * @date 2020/12/18 14:49
 **/
public abstract class AbstractQueryStrategy implements QueryStrategy {


    protected List<Param> getParams(Map param, List<QueryParam> paramList) {
        List<Param> queryParamList = Lists.newArrayList();
        for (QueryParam queryParam : paramList) {
            Param param1 = new Param();
            param1.setName(queryParam.getFiledName());
            param1.setValue(param.get(queryParam.getParamName()));
            queryParamList.add(param1);
        }
        return queryParamList;
    }

    protected List<Param> getParams(Map<String, List<Map>> removeDataMap, RemoveDataTable table) {
        List<Param> paramList = Lists.newArrayList();
        for (DependTable dependTable : table.getDeleteDependTables().getDependTableList()) {
            Param param = new Param();
            param.setName(dependTable.getDependFieldName());
            param.setValue(removeDataMap.get(dependTable.getTableName()));
            paramList.add(param);
        }
        return paramList;
    }



    protected Set<String> getDependFields(String tableName, List<RemoveDataTable> dependTableList) {
        Set<String> fieldSet = Sets.newHashSet();
        for (RemoveDataTable table : dependTableList) {
            List<DependTable> queryDependTables = Optional.ofNullable(table.getQueryDependTables()).map(tables -> tables.getDependTableList()).orElse(null);
            if (queryDependTables != null) {
                for (DependTable queryDependTable : queryDependTables) {
                    if (tableName.equals(queryDependTable.getTableName())) {
                        fieldSet.add(queryDependTable.getDependFieldName());
                    }
                }
            }
            List<DependTable> deleteDependTables = Optional.ofNullable(table.getDeleteDependTables()).map(tables -> tables.getDependTableList()).orElse(null);
            if (deleteDependTables != null) {
                for (DependTable deleteDependTable : deleteDependTables) {
                    if (tableName.equals(deleteDependTable.getTableName())) {
                        fieldSet.add(deleteDependTable.getDependFieldName());
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

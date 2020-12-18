package com.xiaojie.core.service.strategy;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.xiaojie.core.dao.DataBaseOperation;
import com.xiaojie.core.dao.Param;
import com.xiaojie.core.parse.model.QueryParam;
import com.xiaojie.core.parse.model.RemoveDataTable;
import com.xiaojie.core.service.QueryStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 根据<query-params/>标签查询
 *
 * @author wangye
 * @classname QueryParamStrategy
 * @date 2020/12/18 14:47
 **/
public class QueryParamStrategy extends AbstractQueryStrategy {

    @Autowired
    private DataBaseOperation dataBaseOperation;

    @Override
    public List<Map> query(Map param, Map<String, List<RemoveDataTable>> dependTableMap, RemoveDataTable table) {
        List<RemoveDataTable> dependTableList = dependTableMap.get(table.getTableName());
        List<QueryParam> paramList = table.getQueryParams().getParamList();
        List<Map> dataList = null;
        //根据query-param标签生成查询参数
        List<Param> queryParamList = getParams(param, paramList);
        //判断当前表是否被依赖
        if (CollectionUtil.isEmpty(dependTableList)) {
            dataList = dataBaseOperation.selectData(table.getTableName(), "id", queryParamList);
        } else {
            Set<String> fieldSet = getDependFields(table.getTableName(), dependTableList);
            if (CollectionUtil.isEmpty(fieldSet)) {
                String error = String.format("配置文件中缺少depend-field-name依赖字段属性");
                throw new RuntimeException(error);
            } else {
                dataList = dataBaseOperation.selectData(table.getTableName(),
                        CollectionUtil.join(fieldSet, ","), queryParamList);
            }
        }
        return dataList;
    }
}

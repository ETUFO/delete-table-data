package com.xiaojie.core.service.strategy;

import cn.hutool.core.collection.CollectionUtil;
import com.xiaojie.core.dao.DataOperation;
import com.xiaojie.core.dao.Param;
import com.xiaojie.core.parse.model.QueryParam;
import com.xiaojie.core.parse.model.RemoveDataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 根据参数查询数据
 * @author wangye
 * @classname QueryParamStrategy
 * @date 2020/12/18 14:47
 **/
@Component
public class QueryByParamStrategy extends AbstractQueryStrategy {

    @Autowired
    private DataOperation dataOperation;

    @Override
    public List<Map> query(Map param, Map<String, List<RemoveDataTable>> dependTableMap, RemoveDataTable table) {
        List<RemoveDataTable> dependTableList = dependTableMap.get(table.getTableName());
        List<QueryParam> paramList = table.getQueryParams().getParamList();
        //根据query-param标签生成查询参数
        List<Param> queryParamList = getParams(param, paramList);
        //判断当前表是否被依赖
        String fields = getQueryFields(table, dependTableList);
        return dataOperation.selectData(table.getTableName(),fields, queryParamList);
    }
}

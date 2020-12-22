package com.xiaojie.core.service.strategy;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Sets;
import com.xiaojie.core.dao.DataOperation;
import com.xiaojie.core.parse.model.RemoveDataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 默认查询策略，查询表中所有数据
 * 查询字段根据关联表需要的字段，没有关联表则获取主键字段
 * @author wangye
 * @classname DefaultQueryStrategy
 * @date 2020/12/22 14:22
 **/
@Component
public class DefaultQueryStrategy extends AbstractQueryStrategy {

    @Autowired
    private DataOperation dataOperation;

    @Override
    public List<Map> query(Map param, Map<String, List<RemoveDataTable>> dependTableMap, RemoveDataTable table) {
        List<RemoveDataTable> dataTableList = dependTableMap.get(table.getTableName());
        Set<String> fieldSet = getQueryFields(table, dataTableList);
        return dataOperation.selectAll(table.getTableName(), CollectionUtil.join(fieldSet,","));
    }


}

package com.xiaojie.core.service.strategy;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.google.common.collect.Lists;
import com.xiaojie.core.context.TableDataContext;
import com.xiaojie.core.dao.DataOperation;
import com.xiaojie.core.dao.Param;
import com.xiaojie.core.parse.model.DependTable;
import com.xiaojie.core.parse.model.RemoveDataTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 根据依赖表数据查询
 * @author wangye
 * @classname DependTableStrategy
 * @date 2020/12/18 14:51
 **/
@Component
public class QueryByRefTableStrategy extends AbstractQueryStrategy {

    @Autowired
    private DataOperation dataOperation;

    @Override
    public List<Map> query(Map param, Map<String, List<RemoveDataTable>> dependTableMap, RemoveDataTable table) {
        //通过依赖表中字段作为查询条件
        List<DependTable> dependTableList1 = getDependTableList(table);
        List<Map> dataList = Lists.newArrayList();
        Map<String,List<Map>>  removeDataMap = TableDataContext.instance().getData();
        for (DependTable dependTable : dependTableList1) {
            if (!removeDataMap.containsKey(dependTable.getTableName())) {
                return null;
            }
        }
        //判断当前表是否被依赖，生成要查询的字段
        List<RemoveDataTable> dependTableList = dependTableMap.get(table.getTableName());
        Set<String> fieldSet = CollectionUtil.isNotEmpty(dependTableList) ? getDependFields(
                table.getTableName(), dependTableList) : Stream.of("id").collect(Collectors.toSet());
        //从依赖表中获取当前表查询条件值
        List<Param> paramList = getParams(removeDataMap, table);
        if (CollectionUtil.isNotEmpty(paramList)) {
            //查询条件超过MAX_COUNT分多次查询
            int size = paramList.size();
            int maxCount = StringUtils.isNotBlank(table.getDeleteMaxLimit()) ? Convert.toInt(table.getDeleteMaxLimit()) : dataList.size();
            for (int i = 0; i < size; i += maxCount) {
                List<Param> subParamList = paramList.subList(i, i + maxCount > size ? size : i + maxCount);
                dataList.addAll(dataOperation.selectData(table.getTableName(),
                        CollectionUtil.join(fieldSet, ","), subParamList));
            }
            return dataList;
        } else {
            return null;
        }
    }
}

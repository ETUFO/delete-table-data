package com.xiaojie.core.service.strategy;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.xiaojie.core.dao.DataOperation;
import com.xiaojie.core.dao.Param;
import com.xiaojie.core.parse.model.DependTable;
import com.xiaojie.core.parse.model.RemoveDataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 以关联表的数据作为删除条件
 *
 * @author wangye
 * @classname DeleteByDependTableStrategy
 * @date 2020/12/21 9:54
 **/
@Component
public class DeleteByRefTableStrategy extends AbstractDeleteStrategy {

    @Autowired
    private DataOperation dataOperation;

    @Override
    public int delete(Map paramMap, RemoveDataTable table, Map<String, List<Map>> deleteDataMap) {
        List<DependTable> refTableList = table.getDeleteDependTables().getDependTableList();
        List<Param> deleteParams = Lists.newArrayListWithCapacity(refTableList.size());
        for (DependTable refTable : refTableList) {
            Param param = new Param();
            param.setName(StrUtil.toUnderlineCase(refTable.getSourceFieldName()));
            List list = Optional.ofNullable(deleteDataMap.get(refTable.getTableName()))
                    .map(dataList -> {
                        return dataList.stream().map(map -> map.get(refTable.getDependFieldName()))
                                .collect(Collectors.toList());
                    }).orElse(null);
            param.setValue(list);
            deleteParams.add(param);
        }
        return deleteData(table.getTableName(), deleteParams);
    }
}

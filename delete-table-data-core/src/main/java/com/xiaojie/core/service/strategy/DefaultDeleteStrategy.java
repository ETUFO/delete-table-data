package com.xiaojie.core.service.strategy;

import com.xiaojie.core.dao.DataOperation;
import com.xiaojie.core.parse.model.RemoveDataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 默认按照id删除
 *
 * @author wangye
 * @classname DefaultDeleteStrategy
 * @date 2020/12/18 16:53
 **/
@Component
public class DefaultDeleteStrategy extends AbstractDeleteStrategy {
    @Autowired
    private DataOperation dataOperation;

    @Override
    public int delete(Map param, RemoveDataTable table, Map<String, List<Map>> deleteDataMap) {
        List<Map> list = deleteDataMap.get(table.getTableName());
        Object paramValue = null;
        if (list != null) {
            if (list.size() == 1) {
                paramValue = list.get(0).get("id");
            } else {
                paramValue = list.stream().map(map -> map.get("id")).collect(Collectors.toList());
            }
        }
        return deleteData(table.getTableName(), "id", paramValue);
//        return dataOperation.delete(table.getTableName(), "id", paramValue);
    }
}

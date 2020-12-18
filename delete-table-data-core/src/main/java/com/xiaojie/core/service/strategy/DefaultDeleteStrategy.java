package com.xiaojie.core.service.strategy;

import com.xiaojie.core.dao.DataBaseOperation;
import com.xiaojie.core.parse.model.RemoveDataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 默认按照id删除
 * @author wangye
 * @classname DefaultDeleteStrategy
 * @date 2020/12/18 16:53
 **/
@Component
public class DefaultDeleteStrategy extends AbstractDeleteStrategy {
    @Autowired
    private DataBaseOperation dataBaseOperation;
    @Override
    public int delete(Map param, RemoveDataTable table, Map<String, List<Map>> deleteDataMap) {
        List<Map> list = deleteDataMap.get(table.getTableName());
        return dataBaseOperation.delete(table.getTableName(),"id",list);
    }
}

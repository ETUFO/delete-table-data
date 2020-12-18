package com.xiaojie.core.service.strategy;

import com.google.common.collect.Lists;
import com.xiaojie.core.dao.DataBaseOperation;
import com.xiaojie.core.dao.Param;
import com.xiaojie.core.exception.DeleteParamException;
import com.xiaojie.core.parse.model.DeleteParam;
import com.xiaojie.core.parse.model.RemoveDataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 根据参数删除
 * @author wangye
 * @classname DeleteParamStrategy
 * @date 2020/12/18 16:46
 **/
@Component
public class DeleteParamStrategy extends AbstractDeleteStrategy {
    @Autowired
    private DataBaseOperation dataBaseOperation;

    @Override
    public int delete(Map param, RemoveDataTable table,Map<String,List<Map>> deleteDataMap) {
        List<Param> paramList = Lists.newArrayList();
        for (DeleteParam deleteParam : table.getDeleteParams().getDeleteParams()) {
            Param param1 = new Param();
            param1.setName(deleteParam.getFieldName());
            Object value = param.get(deleteParam.getParamName());
            if(value == null){
                throw new DeleteParamException(table.getTableName()+"表,参数"+deleteParam.getParamName()+"为空");
            }
            param1.setValue(value);
        }
        return dataBaseOperation.delete(table.getTableName(),paramList);
    }
}

package com.xiaojie.core.service.strategy;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.xiaojie.core.dao.DataOperation;
import com.xiaojie.core.dao.Param;
import com.xiaojie.core.exception.DeleteParamException;
import com.xiaojie.core.parse.model.DeleteParam;
import com.xiaojie.core.parse.model.RemoveDataTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 根据参数删除1
 * @author wangye
 * @classname DeleteParamStrategy
 * @date 2020/12/18 16:46
 **/
@Slf4j
@Component
public class DeleteByParamStrategy extends AbstractDeleteStrategy {

//    @Autowired
//    public DeleteByParamStrategy(@Qualifier("dataOperation") DataOperation dataOperation){
//        super.dataOperation = dataOperation;
//    }

    @Override
    public int delete(Map paramMap, RemoveDataTable table,Map<String,List<Map>> deleteDataMap) {
        List<Param> paramList = Lists.newArrayList();
        for (DeleteParam deleteParam : table.getDeleteParams().getDeleteParams()) {
            Param param = new Param();
            param.setName(StrUtil.toUnderlineCase(deleteParam.getFieldName()));
            Object value = paramMap.get(deleteParam.getParamName());
            if(value == null){
                throw new DeleteParamException(table.getTableName()+"表,参数"+deleteParam.getParamName()+"为空");
            }
            param.setValue(value);
            paramList.add(param);
        }
        return deleteData(table.getTableName(),paramList);
    }
}

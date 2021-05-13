package com.xiaojie.core.service.strategy;

import cn.hutool.core.collection.CollectionUtil;
import com.xiaojie.core.dao.DataOperation;
import com.xiaojie.core.dao.Param;
import com.xiaojie.core.service.DeleteStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * 删除抽象逻辑
 *
 * @author wangye123
 * @classname AbstractDeleteStrategy
 * @date 2020/12/18 16:26
 **/
@Slf4j
public abstract class AbstractDeleteStrategy implements DeleteStrategy {
    @Autowired
    protected DataOperation dataOperation;

    protected int deleteData(String tableName, List<Param> params) {
        if (!checkParam(params, tableName))
            return 0;
        return dataOperation.delete(tableName, params);
    }

    protected int deleteData(String tableName, String paramName, Object paramValue) {
        if (!checkParam(paramValue, tableName))
            return 0;
        return dataOperation.delete(tableName, paramName, paramValue);
    }

    private boolean checkParam(Object param, String tableName) {
        if (param != null) {
            if (!(param instanceof Collection)) {
                return true;
            } else if (CollectionUtil.isNotEmpty((Collection) param)) {
                return true;
            }
        }
        log.info(String.format("%s表删除0条数据,参数为空", tableName));
        return false;
    }
}

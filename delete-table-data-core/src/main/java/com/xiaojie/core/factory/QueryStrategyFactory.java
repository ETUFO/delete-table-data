package com.xiaojie.core.factory;

import com.xiaojie.core.parse.model.DeleteParam;
import com.xiaojie.core.parse.model.DependTable;
import com.xiaojie.core.parse.model.QueryParam;
import com.xiaojie.core.parse.model.RemoveDataTable;
import com.xiaojie.core.service.QueryStrategy;
import com.xiaojie.core.service.strategy.DefaultQueryStrategy;
import com.xiaojie.core.service.strategy.QueryByParamStrategy;
import com.xiaojie.core.service.strategy.QueryByRefTableStrategy;
import com.xiaojie.core.toolkits.CustSpringContextUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * 根据配置文件生成查询策略
 * 如果未配置查询方式则返回null
 *
 * @author wangye
 * @classname QueryStrategyFactory
 * @date 2020/12/22 13:54
 **/
public class QueryStrategyFactory {

    public static QueryStrategy createQueryStrategy(RemoveDataTable table) {

        //配置删除条件则不需要查询数据
        List<DeleteParam> deleteParamsList = Optional.ofNullable(table.getDeleteParams()).map(deleteParams -> deleteParams.getDeleteParams()).orElse(null);
        List<DependTable> deleteDependTableList = Optional.ofNullable(table.getDeleteDependTables()).map(deleteDependTables -> deleteDependTables.getDependTableList()).orElse(null);
        if (deleteParamsList != null || deleteDependTableList != null) {
            return null;
        }
        String extBeanName = Optional.ofNullable(table.getQueryExtensionClass()).map(extension -> extension.getBeanName()).orElse(null);
        if (StringUtils.isNotBlank(extBeanName)) {
            return CustSpringContextUtil.getBean(extBeanName);
        }
        List<QueryParam> queryParamsList = Optional.ofNullable(table.getQueryParams()).map(queryParams -> queryParams.getParamList()).orElse(null);
        if (queryParamsList != null) {
            return CustSpringContextUtil.getBean(QueryByParamStrategy.class);
        }
        List<DependTable> dependTableList = Optional.ofNullable(table.getQueryDependTables()).map(dependTables -> dependTables.getDependTableList()).orElse(null);
        if (dependTableList != null) {
            return CustSpringContextUtil.getBean(QueryByRefTableStrategy.class);
        }
        return CustSpringContextUtil.getBean(DefaultQueryStrategy.class);
    }

}

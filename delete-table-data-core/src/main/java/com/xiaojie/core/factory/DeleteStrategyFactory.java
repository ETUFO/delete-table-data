package com.xiaojie.core.factory;

import cn.hutool.extra.spring.SpringUtil;
import com.xiaojie.core.parse.model.DeleteParam;
import com.xiaojie.core.parse.model.DependTable;
import com.xiaojie.core.parse.model.RemoveDataTable;
import com.xiaojie.core.service.DeleteService;
import com.xiaojie.core.service.DeleteStrategy;
import com.xiaojie.core.service.strategy.DefaultDeleteStrategy;
import com.xiaojie.core.service.strategy.DeleteByParamStrategy;
import com.xiaojie.core.service.strategy.DeleteByRefTableStrategy;
import com.xiaojie.core.toolkits.CustSpringContextUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author wangye
 * @classname DeleteStrategyFactory
 * @date 2020/12/22 13:45
 **/
public class DeleteStrategyFactory {

    public static DeleteStrategy createDeleteStrategy(RemoveDataTable table) {

        String extensionBeanName = Optional.ofNullable(table.getDeleteExtensionClass()).map(extensionClass -> extensionClass.getBeanName()).orElse(null);
        if (StringUtils.isNotBlank(extensionBeanName)) {
            return CustSpringContextUtil.getBean(extensionBeanName);
        }
        List<DeleteParam> deleteParamList = Optional.ofNullable(table.getDeleteParams()).map(deleteParams -> deleteParams.getDeleteParams()).orElse(null);
        if (deleteParamList != null) {
            return CustSpringContextUtil.getBean(DeleteByParamStrategy.class);
        }
        List<DependTable> dependTableList = Optional.ofNullable(table.getDeleteDependTables()).map(dependTables -> dependTables.getDependTableList()).orElse(null);
        if (dependTableList != null) {
            return CustSpringContextUtil.getBean(DeleteByRefTableStrategy.class);
        }
        return CustSpringContextUtil.getBean(DefaultDeleteStrategy.class);
    }

}

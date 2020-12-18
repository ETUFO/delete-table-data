package com.xiaojie.core.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaojie.autoconfigure.DeleteTableDataProperties;
import com.xiaojie.core.cache.Cache;
import com.xiaojie.core.context.DeleteLogContext;
import com.xiaojie.core.context.TableDataContext;
import com.xiaojie.core.dao.DataBaseOperation;
import com.xiaojie.core.dao.Param;
import com.xiaojie.core.extension.CustomerQuery;
import com.xiaojie.core.parse.RemoveExamDataXmlParse;
import com.xiaojie.core.parse.model.*;
import com.xiaojie.core.service.strategy.DependTableStrategy;
import com.xiaojie.core.service.strategy.QueryParamStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 生成删除据所需要的的参数
 *
 * @author wangye
 * @classname GenerateDeleteParam
 * @date 2020/12/16 14:10
 **/
@Component
public class GenerateDeleteParam {

    @Autowired
    private DeleteTableDataProperties deleteTableDataProperties;
    @Autowired
    private Cache defualtCache;
    @Autowired
    private DataBaseOperation dataBaseOperation;

    public void removeData(String fileName, Map<String, Object> param) {
        try {
            TableDataContext.instance().setFileName(fileName);
            //获取删除表模型
            RemoveDataTables removeDataTables = getRemoveDataTables(fileName);
            List<RemoveDataTable> tableList = removeDataTables.getRemoveDataTableList();
            //整合数据表之间依赖关系
            Map<String, List<RemoveDataTable>> dependTableMap = Maps.newHashMap();
            for (RemoveDataTable removeDataTable : tableList) {
                List<DependTable> dependTableList = getDependTableList(removeDataTable);
                for (DependTable dependTable : dependTableList) {
                    List<RemoveDataTable> list = dependTableMap.get(dependTable.getTableName());
                    if (list == null) {
                        list = Lists.newArrayList();
                        dependTableMap.put(dependTable.getTableName(), list);
                    }
                    list.add(removeDataTable);
                }
            }
            //TODO 根据before-table、after-table属性进行排序
            //获取要删除的数据
            Map<String, List<Map>> removeDataMap = getRemoveData(param, tableList, dependTableMap);
            //删除数据
        } catch (Exception e) {
            throw e;
        } finally {
            TableDataContext.instance().remove();
            DeleteLogContext.instance().remove();
        }
    }


    private Map<String, List<Map>> getRemoveData(Map param, List<RemoveDataTable> tableList, Map<String, List<RemoveDataTable>> dependTableMap) {
        //获取要删除的数据
        Map<String, List<Map>> removeDataMap = Maps.newHashMap();
        //删除的数据放入上下文中
        TableDataContext.instance().setData(removeDataMap);
        //避免死循环
        int count = -1;
        while (removeDataMap.size() < tableList.size() && count != removeDataMap.size()) {
            count = removeDataMap.size();
            outer:
            for (RemoveDataTable table : tableList) {
                //数据已经获取，跳过当前循环
                if (removeDataMap.containsKey(table.getTableName())) continue;
                //使用扩展查询实现类
                String beanName = Optional.ofNullable(table.getQueryExtensionClass()).map(extension -> extension.getBeanName()).orElse(null);
                if (StringUtils.isNotEmpty(beanName)) {
                    CustomerQuery customerQuery = SpringUtil.getBean(beanName);
                    List list = customerQuery.get(table.getTableName(), param);
                    removeDataMap.put(table.getTableName(), list);
                }
                //根据不同策略进行查询
                boolean flag = Optional.of(table.getQueryParams()).map(queryParams -> queryParams.getParamList() != null).orElse(false);
                QueryStrategy queryStrategy = flag ? SpringUtil.getBean(QueryParamStrategy.class) : SpringUtil.getBean(DependTableStrategy.class);
                List<Map> tableDataList = queryStrategy.query(param, dependTableMap, table);
                if (CollectionUtil.isNotEmpty(tableDataList)) {
                    removeDataMap.put(table.getTableName(), tableDataList);
                    DeleteLogContext.instance().getTableMap().remove(table.getTableName());
                } else {
                    DeleteLogContext.instance().getTableMap().put(table.getTableName(),table);
                }
            }
        }
        return removeDataMap;

    }


    private RemoveDataTables getRemoveDataTables(String fileName) {
        String cacheName = deleteTableDataProperties.getCache();
        Cache cache = StringUtils.isBlank(cacheName) ? defualtCache : SpringUtil.getBean(cacheName);
        String json = cache.get(fileName);
        RemoveDataTables removeDataTables = null;
        if (StringUtils.isNotBlank(json)) {
            removeDataTables = JSON.parseObject(json, RemoveDataTables.class);
        } else {
            removeDataTables = RemoveExamDataXmlParse.getRemoveDataTables(fileName);
        }
        if (removeDataTables == null) {
            throw new RuntimeException("获取配置文件错误");
        }
        return removeDataTables;
    }

    private List<DependTable> getDependTableList(RemoveDataTable table) {
        List<DependTable> list = Lists.newArrayList();
        List<DependTable> list2 = Lists.newArrayList();
        DependTables queryDependTables = table.getQueryDependTables();
        list = queryDependTables == null ? list :
                Optional.ofNullable(queryDependTables.getDependTableList()).map(dependList -> dependList).orElse(Lists.newArrayList());
        DependTables deleteDependTables = table.getDeleteDependTables();
        list2 = deleteDependTables == null ? list2 :
                Optional.ofNullable(deleteDependTables.getDependTableList()).map(dependList -> dependList).orElse(Lists.newArrayList());
        list.addAll(list2);
        return list;
    }
}

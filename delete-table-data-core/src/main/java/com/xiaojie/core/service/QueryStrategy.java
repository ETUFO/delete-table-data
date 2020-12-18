package com.xiaojie.core.service;

import com.xiaojie.core.parse.model.RemoveDataTable;

import java.util.List;
import java.util.Map;

/**
 * @classname: QueryStrategy
 * @description: TODO
 * @author: wangye
 * @date: 2020/12/18 14:46
 **/
public interface QueryStrategy {

    List<Map> query(Map param, Map<String, List<RemoveDataTable>> dependTableMap, RemoveDataTable table);

}

package com.xiaojie.core.service;

import com.xiaojie.core.parse.model.RemoveDataTable;

import java.util.List;
import java.util.Map;

/**
 * 删除逻辑
 * @classname: DeleteStrategy
 * @description: TODO
 * @author: wangye
 * @date: 2020/12/18 16:26
 **/
public interface DeleteStrategy {

    int delete(Map param, RemoveDataTable table,Map<String,List<Map>> deleteDataMap);
}

package com.xiaojie.core.cache;

import com.xiaojie.core.parse.model.RemoveDataTable;
import com.xiaojie.core.parse.model.RemoveDataTables;

/**
 * 缓存接口
 *
 * @classname: Cache
 * @author: wangye
 * @date: 2020/12/16 14:13
 **/
public interface Cache {

    RemoveDataTables get(String fileName);

    void save(String key, RemoveDataTables tables);
}

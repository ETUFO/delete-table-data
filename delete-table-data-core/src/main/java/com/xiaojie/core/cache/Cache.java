package com.xiaojie.core.cache;

/**
 * 缓存接口
 *
 * @classname: Cache
 * @description: TODO
 * @author: wangye
 * @date: 2020/12/16 14:13
 **/
public interface Cache {

    String get(String fileName);

    void save(String fileName, String model);
}

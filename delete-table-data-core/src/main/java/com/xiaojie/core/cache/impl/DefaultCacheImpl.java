package com.xiaojie.core.cache.impl;

import com.xiaojie.core.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认缓存实现类
 * @author wangye
 * @classname DefaultCacheImpl
 * @date 2020/12/16 15:31
 **/
@Component
public class DefaultCacheImpl implements Cache {

    private final static ConcurrentHashMap<String,String> MAP = new ConcurrentHashMap<>();

    @Override
    public String get(String fileName) {
        return MAP.get(fileName);
    }

    @Override
    public void save(String fileName, String model) {
        MAP.putIfAbsent(fileName,model);
    }
}

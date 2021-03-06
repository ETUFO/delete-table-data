package com.xiaojie.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 配置文件
 * @author wangye
 * @classname DeleteTableDataProperties
 * @date 2020/12/16 15:23
 **/
@Data
@Component
@ConfigurationProperties(prefix = "delete.table.data")
public class DeleteTableDataProperties {
    //缓存实现类
    private String cache;

    private String filePath;

}

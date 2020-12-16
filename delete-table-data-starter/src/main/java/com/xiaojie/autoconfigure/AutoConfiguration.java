package com.xiaojie.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author wangye
 * @classname AutoConfiguration
 * @date 2020/12/16 13:50
 **/
@Configuration
@ComponentScan({"com.xiaojie","cn.hutool"})
public class AutoConfiguration {
}

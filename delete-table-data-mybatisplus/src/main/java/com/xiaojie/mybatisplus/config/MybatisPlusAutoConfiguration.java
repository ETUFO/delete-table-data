package com.xiaojie.mybatisplus.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangye
 * @classname MybatisPlusAutoConfiguration
 * @date 2020/12/21 11:06
 **/
@Configuration
@MapperScan(basePackages = {"com.xiaojie.**.mapper", "com.xiaojie.**.dao"})
public class MybatisPlusAutoConfiguration {
}

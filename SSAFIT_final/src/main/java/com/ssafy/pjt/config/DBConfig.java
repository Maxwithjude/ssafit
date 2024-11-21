package com.ssafy.pjt.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.ssafy.pjt.model.dao")
@Configuration
public class DBConfig {

}

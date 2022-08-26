package com.nathan.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.nathan.store.mapper") //指定mapper接口路径，项目启动时自动加载接口文件
public class JavaProjectStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaProjectStoreApplication.class, args);
	}

}

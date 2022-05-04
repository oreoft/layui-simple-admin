package com.someget.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author zyf
 * @date 2022-03-25 20:03
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.someget.admin.common.sys.dal.mapper", "com.someget.admin.dal.mapper"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}

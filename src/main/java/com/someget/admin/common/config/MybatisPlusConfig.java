//package com.someget.admin.common.config;
//
//import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
///**
// * mp的分页插件和sql打印监控
// * @author zyf
// * @date 2022-03-25 20:03
// */
//@Configuration
//public class MybatisPlusConfig {
//
//    /***
//     * plus 的性能优化
//     * @return
//     */
//    @Bean
//	@Profile("dev")
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
//        //本地连接测试库环境不稳定，所以不能设置sql执行最长时间，正式环境也不能设置，容易超时
////		performanceInterceptor.setMaxTime(1000);
//        /*<!--SQL是否格式化 默认false-->*/
////		performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }
//
//    /**
//     * mybatis-plus分页插件
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor page = new PaginationInterceptor();
//        page.setDialectType("mysql");
//        return page;
//    }
//
//
//}

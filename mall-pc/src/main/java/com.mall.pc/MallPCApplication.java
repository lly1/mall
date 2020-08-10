package com.mall.pc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lly
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(value = "com.mall.com.mall.provider.dao")
public class MallPCApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MallPCApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MallPCApplication.class, args);
    }

}

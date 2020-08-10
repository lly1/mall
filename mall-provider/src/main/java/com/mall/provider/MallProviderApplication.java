package com.mall.provider;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lly
 */
@EnableDubboConfiguration
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(value = "com.mall.provider.dao")
public class MallProviderApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MallProviderApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MallProviderApplication.class, args);
    }

}

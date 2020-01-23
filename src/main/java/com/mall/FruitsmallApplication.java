package com.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.mall.dao")
public class FruitsmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(FruitsmallApplication.class, args);
    }

}

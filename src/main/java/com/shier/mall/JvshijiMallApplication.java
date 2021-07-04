package com.shier.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.shier.mall.dao") //添加 @Mapper 注解
public class JvshijiMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(JvshijiMallApplication.class, args);
    }

}

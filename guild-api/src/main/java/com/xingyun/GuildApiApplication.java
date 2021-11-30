package com.xingyun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.xingyun.dao.mapper")
public class GuildApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuildApiApplication.class, args);
    }
}

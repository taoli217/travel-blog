package com.travelblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.travelblog.mapper")
public class TravelBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelBlogApplication.class, args);
    }
}

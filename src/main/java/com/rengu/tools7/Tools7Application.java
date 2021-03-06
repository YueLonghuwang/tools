package com.rengu.tools7;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan("com.rengu.tools7.mapper")
@SpringBootApplication

public class Tools7Application {

    public static void bb(String[] args) {
        SpringApplication.run(Tools7Application.class, args);
    }

}

package cn.edu.neu.visualservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.edu.neu.visualservice.mapper")
public class VisualServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisualServiceApplication.class, args);
    }
}

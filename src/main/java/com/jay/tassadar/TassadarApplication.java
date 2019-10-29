package com.jay.tassadar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TassadarApplication {

    public static void main(String[] args) {
        SpringApplication.run(TassadarApplication.class, args);
    }

}

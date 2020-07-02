package com.lhsz.bandian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.lhsz.bandian.mapper","com.lhsz.bandian.*.mapper"})
@SpringBootApplication
public class BandianApplication {

    public static void main(String[] args) {
        SpringApplication.run(BandianApplication.class, args);
    }

}

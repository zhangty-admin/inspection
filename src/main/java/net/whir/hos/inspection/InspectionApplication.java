package net.whir.hos.inspection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */


@SpringBootApplication
@MapperScan(basePackages = {"net.whir.hos.inspection.*.dao"})
public class InspectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(InspectionApplication.class, args);
    }

}

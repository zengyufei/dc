package com.zyf.dc;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling   // 开启定时任务
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement  // 开启事务
@MapperScan("com.zyf.dc.mapper") // mapper 接口扫描
public class DianCanApplication {

    private static Logger log = LoggerFactory.getLogger(DianCanApplication.class);

    public static void main(String[] args) {
        log.info("============ DiancanApplication 启动================");
        SpringApplication.run(DianCanApplication.class, args);
    }

}

package com.zheng;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郑超
 * @create 2021/6/3
 */
@SpringBootApplication
public class NacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }

    // 支持配置动态刷新，在配置中心的相关配置发生变化以后，能够及时看到更新——原理是发生变化并访问时创建了一个新的实例
    @RefreshScope
    @RestController
    @RequestMapping("/config/")
    public class NacosConfigController {

        @Value("${logging.level.com.zheng:info}")
        private String logLevel;

        @GetMapping("/doGetLogLevel")
        public String getLogLevel() {
            return "Log level is " + logLevel;
        }

        @Value("${server.tomcat.threads.max:200}")
        private Integer serverThreadMax;

        @RequestMapping("/doGetServerThreadMax")
        public String doGetServerThreadMax() {
            return "server.threads.max is  " + serverThreadMax;
        }

        @Value("${page.pageSize:10}")
        private Integer pageSize;

        @RequestMapping("/doGetPageSize")
        public String doGetPageSize() {
            return "server.threads.max is  " + pageSize;
        }
    }
}

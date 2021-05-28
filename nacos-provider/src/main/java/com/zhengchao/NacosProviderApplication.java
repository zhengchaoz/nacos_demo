package com.zhengchao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务提供方，向nacos注册服务
 *
 * @author 郑超
 * @create 2021/5/28
 */
@SpringBootApplication
public class NacosProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication.class, args);
    }

    @Value("${server.port}")
    private String server;

    @RestController
    public class ProviderController {

        @GetMapping(value = "/provider/echo/{string}")
        public String doEcho(@PathVariable String string) {
            return server + "say:Hello Nacos Discovery " + string;
        }
    }

}

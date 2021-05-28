package com.zhengchao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 服务消费方
 *
 * @author 郑超
 * @create 2021/5/28
 */
@SpringBootApplication
public class NacosConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RestController
    public class ConsumerController {

        @Value("${spring.application.name}")
        private String appName;

        @Autowired
        private LoadBalancerClient loadBalancerClient;

        @Autowired
        private RestTemplate restTemplate;

        @GetMapping("/consumer/doRestEcho")
        public String doRestEcho() {
            ServiceInstance choose = loadBalancerClient.choose("nacos-provider");
            String url = String.format("http://localhost:8081/provider/echo/zhengchao",
                    choose.getHost(), choose.getPort(), appName);
            System.out.println("request url:" + url);
            return restTemplate.getForObject(url, String.class);
        }
    }

}

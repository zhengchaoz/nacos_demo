package com.zhengchao.controller;

import com.zhengchao.feign.ConsumerHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郑超
 * @create 2021/6/1
 */
@RestController
@RequestMapping("/consumer/feign/")
public class FeignConsumerController {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private ConsumerHttpApi consumerHttpApi;

    @GetMapping
    public String diFeignEcho() {
        return consumerHttpApi.echoMessage(appName);
    }

}

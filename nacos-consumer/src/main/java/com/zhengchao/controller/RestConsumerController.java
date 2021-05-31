package com.zhengchao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 服务消费方
 * 通过此案例演示rest请求的处理
 *
 * @author 郑超
 * @create 2021/5/31
 */
@RestController
@RequestMapping("/consumer/template/")
public class RestConsumerController {

    // 通过此对象进行远程过程调用——RPC
    @Autowired
    private RestTemplate loadBalancedRestTemplate;

    @DeleteMapping("{id}")
    public String doDelete(@PathVariable Integer id) {
        String url = String.format("http://%s/provider/template/%s", "nacos-provider", id);
        loadBalancedRestTemplate.delete(url);
        return "delete ok";
    }

    @PostMapping
    public Map<String, Object> doPost(@RequestBody Map<String, Object> map) {
        //定义服务提供方的url
        String url = String.format("http://%s/provider/template/", "nacos-provider");
        return loadBalancedRestTemplate.postForObject(url,
                map,//这里map表示要提交到服务提供方的数据
                Map.class//这个类型通常对应响应结果类型
        );
    }

    @PutMapping
    public String doPut(@RequestBody Map<String, Object> map) {
        //定义服务提供方的url
        String url = String.format("http://%s/provider/template/", "nacos-provider");
        loadBalancedRestTemplate.put(url, map);//这里map表示要提交到服务提供方的数据
        return "put ok";
    }

}

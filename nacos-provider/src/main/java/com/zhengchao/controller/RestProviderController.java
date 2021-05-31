package com.zhengchao.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 服务提供方
 * 通过此案例演示rest请求的处理
 *
 * @author 郑超
 * @create 2021/5/31
 */
@RestController
@RequestMapping("/provider/template/")
public class RestProviderController {

    @Value("${server.port}")
    private String server;

    @DeleteMapping("{id}")
    public void doDelete(@PathVariable Integer id) {
        System.out.println(id + " is delete by " + server);
        // throw new RuntimeException("delete error");
    }

    @PostMapping
    public Map<String, Object> doPost(
            @RequestBody Map<String, Object> map) {
        System.out.println("consumer post data: " + map);
        map.put("status", 1);
        map.put("server.port", server);
        return map;
    }

    @PutMapping
    public void doPut(@RequestBody Map<String, Object> map) {
        System.out.println("consumer put data: " + map);
    }

}

package com.zhengchao.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定义Http请求API，基于此API借助OpenFeign访问远端服务
 *
 * @author 郑超
 * @create 2021/6/1
 */
@FeignClient(name = "nacos-provider")// nacos-providers为服务提供者名称
@RestController// 可以省略
public interface ConsumerHttpApi {

    @GetMapping("/provider/echo/{str}")
    public String echoMessage(@PathVariable String str);

}

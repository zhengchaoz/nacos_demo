package com.zheng.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 定制流控网关返回值
 *
 * @author 郑超
 * @create 2021/6/7
 */
@Configuration
public class GatewayConfig {

    public GatewayConfig() {
        GatewayCallbackManager.setBlockHandler((serverWebExchange, throwable) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("state", 429);
            map.put("message", "===定制流控网关返回值===请求太多");

            String jsonStr = JSON.toJSONString(map);
            return ServerResponse.ok().body(Mono.just(jsonStr), String.class);
        });
    }
}

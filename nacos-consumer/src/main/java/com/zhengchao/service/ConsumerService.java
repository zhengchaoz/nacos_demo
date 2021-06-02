package com.zhengchao.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * @author 郑超
 * @create 2021/6/2
 */
@Service
public class ConsumerService {

    // @SentinelResource可以通过指定的链路进行流量统计并执行限流操作
    @SentinelResource("doGetReference")
    public String doGetReference() {
        return "do get reference";
    }
}

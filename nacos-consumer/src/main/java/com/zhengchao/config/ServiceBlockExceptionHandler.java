package com.zhengchao.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 自己定义Sentinel中异常处理
 *
 * @author 郑超
 * @create 2021/6/2
 */
@Component
public class ServiceBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        //response.setStatus(601);
        //设置响应数据的编码
        response.setCharacterEncoding("utf-8");
        //告诉客户端要响应的数据类型以及客户端以什么编码呈现数据
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        Map<String, Object> map = new HashMap<>();
        if (e instanceof DegradeException) {//降级、熔断
            map.put("status", 601);
            map.put("message", "服务被熔断了!");
        } else if (e instanceof FlowException) {
            map.put("status", 602);
            map.put("message", "服务被限流了!");
        } else {
            map.put("status", 603);
            map.put("message", "Blocked by Sentinel (flow limiting)");
        }
        //将map对象转换为json格式字符串
        String jsonStr = new ObjectMapper().writeValueAsString(map);
        pw.println(jsonStr);
        pw.flush();
    }
}

package com.zhengchao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>服务消费方</p>
 * 两种客户端负载均衡：<br>
 * 1.默认是Nacos集成了Ribbon来实现的，Ribbon配合RestTemplate，可以非常容易的实现服务之间的访问。
 *   没有访问nacos，缺点是不支持高并发<br>
 * 封装前，代码实现：
 * <pre>{@code
 *          @Autowired
 *          private LoadBalancerClient loadBalancerClient;
 *
 *          ServiceInstance choose = loadBalancerClient.choose("nacos-provider");
 *          String url = String.format("http://%s:%s/provider/echo/%s",
 *                  choose.getHost(), choose.getPort(), appName);
 *      }</pre>
 * <p>
 * 2.在RestTemplate上增加@LoadBalanced注解，使用服务名的方式远程访问，该注解是Spring自带的负载均衡器.
 * 封装后，代码实现：
 * <pre>{@code
 *          @EnableDiscoveryClient
 *          @SpringBootApplication
 *          public class NacosConsumerApplication
 *
 *          @LoadBalanced
 *          @Bean
 *          public RestTemplate restTemplate() { return new RestTemplate(); }
 *
 *          String url = String.format("http://nacos-provider/provider/echo/%s", appName);
 *      }</pre>
 * <strong>特别注意：以上两种方式不能同时使用！！</strong>
 *
 * @author 郑超
 * @create 2021/5/28
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class NacosConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }

    @LoadBalanced// 表示让restTemplate具备了负载均衡的特性
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RestController
    public class ConsumerController {

        @Value("${spring.application.name}")
        private String appName;

        // 根据服务名查找对应服务——调用方法choose("服务名")，返回具体的服务实例（ServiceInstance），
        // 然后可以利用ServiceInstance获得服务的ip和端口号
//        @Autowired
//        private LoadBalancerClient loadBalancerClient;

        @Autowired
        private RestTemplate restTemplate;

        @GetMapping("/consumer/doRestEcho")
        public String doRestEcho() {
//            ServiceInstance choose = loadBalancerClient.choose("nacos-provider");
//            String url = String.format("http://%s:%s/provider/echo/%s",
//                    choose.getHost(), choose.getPort(), appName);
            // 用服务名访问提供者，需要@EnableDiscoveryClient和@LoadBalanced
            String url = String.format("http://nacos-provider/provider/echo/%s", appName);
            System.out.println("request url:" + url);
            return restTemplate.getForObject(url, String.class);
        }

        @GetMapping("/consumer/doLoadBalanced")
        public String doLoadBalanced() {
            String url = String.format("http://nacos-provider/provider/echo/%s", appName);
            System.out.println("request url:" + url);
            return restTemplate.getForObject(url, String.class);
        }
    }

}

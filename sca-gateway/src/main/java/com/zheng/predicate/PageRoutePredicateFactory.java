package com.zheng.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义Predicate断言规则
 *
 * @author 郑超
 * @create 2021/6/4
 */
@Component
public class PageRoutePredicateFactory extends AbstractRoutePredicateFactory<PageRoutePredicateFactory.Config> {

    public PageRoutePredicateFactory() {
        super(PageRoutePredicateFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        // 这里的顺序要跟配置文件中的参数顺序一致
        return Arrays.asList("minPage", "maxPage");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return serverWebExchange -> {
            String page = serverWebExchange.getRequest().getQueryParams().getFirst("Page");
            if (StringUtils.hasText(page)) {
                int p = Integer.parseInt(page);
                return p > config.getMinPage() && p < config.getMaxPage();
            }
            return true;
        };
    }

    static class Config {
        private int minPage;
        private int maxPage;

        public int getMinPage() {
            return minPage;
        }

        public void setMinPage(int minPage) {
            this.minPage = minPage;
        }

        public int getMaxPage() {
            return maxPage;
        }

        public void setMaxPage(int maxPage) {
            this.maxPage = maxPage;
        }
    }
}

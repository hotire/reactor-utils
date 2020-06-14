package com.github.hotire.reactor.utils.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class RequestHeaderWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        exchange.getRequest()
                .getHeaders()
                .forEach((key, value) -> log.debug("key : [{}] / value : {}", key, value));
        return chain.filter(exchange);
    }
}

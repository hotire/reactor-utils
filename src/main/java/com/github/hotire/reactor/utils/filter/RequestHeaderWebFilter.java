package com.github.hotire.reactor.utils.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class RequestHeaderWebFilter implements WebfluxFilter {

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
        return chain.filter(exchange)
                    .doOnSubscribe(subscription -> exchange.getRequest()
                                                           .getHeaders()
                                                           .forEach((key, value) -> log.debug("key : [{}] / value : {}", key, value)));
    }

    @Override
    public Mono<ServerResponse> filter(final ServerRequest serverRequest, final HandlerFunction<ServerResponse> handlerFunction) {
        return handlerFunction.handle(serverRequest)
                              .doOnSubscribe(subscription -> serverRequest.exchange()
                                                                          .getRequest()
                                                                          .getHeaders()
                                                                          .forEach((key, value) -> log.debug("key : [{}] / value : {}", key, value)));
    }
}

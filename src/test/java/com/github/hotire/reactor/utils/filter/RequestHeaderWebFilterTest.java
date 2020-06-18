package com.github.hotire.reactor.utils.filter;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestHeaderWebFilterTest {

    @Test
    void filter() {
        // given
        final RequestHeaderWebFilter webFilter = new RequestHeaderWebFilter();
        final ServerHttpRequest request = mock(ServerHttpRequest.class);
        final ServerWebExchange exchange = mock(ServerWebExchange.class);

        final WebFilterChain chain = mock(WebFilterChain.class);

        // when
        when(exchange.getRequest()).thenReturn(request);
        when(request.getHeaders()).thenReturn(HttpHeaders.EMPTY);
        when(chain.filter(exchange)).thenReturn(Mono.empty());
        final Mono<Void> result = webFilter.filter(exchange, chain);

        StepVerifier.create(result)
                    .verifyComplete();
    }

    @Test
    void filterFunction() {
        // given
        final RequestHeaderWebFilter webFilter = new RequestHeaderWebFilter();
        final ServerRequest request = mock(ServerRequest.class);
        final ServerHttpRequest serverHttpRequest = mock(ServerHttpRequest.class);
        final ServerWebExchange exchange = mock(ServerWebExchange.class);
        final HandlerFunction<ServerResponse> function = mock(HandlerFunction.class);

        // when
        when(request.exchange()).thenReturn(exchange);
        when(exchange.getRequest()).thenReturn(serverHttpRequest);
        when(serverHttpRequest.getHeaders()).thenReturn(HttpHeaders.EMPTY);
        when(function.handle(request)).thenReturn(Mono.empty());
        final Mono<ServerResponse> result = webFilter.filter(request, function);

        // then
        StepVerifier.create(result)
                    .verifyComplete();
    }
}
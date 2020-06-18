package com.github.hotire.reactor.utils.filter;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

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
        webFilter.filter(exchange, chain);

        // no assert
    }
}
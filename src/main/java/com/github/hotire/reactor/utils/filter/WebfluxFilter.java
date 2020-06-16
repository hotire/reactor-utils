package com.github.hotire.reactor.utils.filter;

import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;

public interface WebfluxFilter extends WebFilter, HandlerFilterFunction<ServerResponse, ServerResponse> {
}

package com.github.hotire.reactor.utils.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ClientCodecConfigurer;

import java.util.List;
import java.util.function.Consumer;

@Configuration
public class WebClientConfig {
    private List<Consumer<ClientCodecConfigurer>> consumers;
}

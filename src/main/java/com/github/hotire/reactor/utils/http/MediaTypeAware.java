package com.github.hotire.reactor.utils.http;

import org.springframework.http.MediaType;

@FunctionalInterface
public interface MediaTypeAware {
    MediaType getMediaType();
}

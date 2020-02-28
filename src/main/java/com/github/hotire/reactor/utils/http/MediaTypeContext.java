package com.github.hotire.reactor.utils.http;

import org.springframework.http.MediaType;

import java.util.Optional;

public interface MediaTypeContext extends MediaTypeAware {
    default boolean equals(final MediaType mediaType) {
        return Optional.ofNullable(mediaType)
                       .map(it -> getMediaType().equals(it) || getMediaType().toString().equals(it.toString()))
                       .orElse(false);
    }
}

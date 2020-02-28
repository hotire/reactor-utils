package com.github.hotire.reactor.utils.http;

import org.springframework.http.MediaType;

public class MediaTypeAdapter implements MediaTypeContext {
    private final MediaType mediaType;

    public MediaTypeAdapter(final MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public static MediaTypeAdapter of(final MediaType mediaType) {
        return new MediaTypeAdapter(mediaType);
    }

    @Override
    public MediaType getMediaType() {
        return mediaType;
    }
}

package com.github.hotire.reactor.utils.codec;

import com.github.hotire.reactor.utils.http.MediaTypeAdapter;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageWriter;
import reactor.core.publisher.Mono;

import java.util.Map;

public class Utf8HttpMessageWriterDecorator<T> extends HttpMessageWriterDecorator<T>{

    private final MediaTypeAdapter applicationJson;

    public Utf8HttpMessageWriterDecorator(final HttpMessageWriter<T> delegate) {
        super(delegate);
        applicationJson = MediaTypeAdapter.of(MediaType.APPLICATION_JSON);
    }

    @SuppressWarnings("deprecation")
    @Override
    public Mono<Void> write(final Publisher<? extends T> publisher, final ResolvableType resolvableType, MediaType mediaType, final ReactiveHttpOutputMessage reactiveHttpOutputMessage, final Map<String, Object> hints) {
        if (applicationJson.equals(mediaType)) {
            mediaType = MediaType.APPLICATION_JSON_UTF8;
        }

        if (applicationJson.equals(reactiveHttpOutputMessage.getHeaders().getContentType())) {
            reactiveHttpOutputMessage.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        }

        return getDelegate().write(publisher, resolvableType, mediaType, reactiveHttpOutputMessage, hints);
    }
}

package com.github.hotire.reactor.utils.codec;

import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageWriter;
import reactor.core.publisher.Mono;

import java.util.Map;

public class Utf8HttpMessageWriterDecorator<T> extends HttpMessageWriterDecorator<T>{
    public Utf8HttpMessageWriterDecorator(HttpMessageWriter<T> delegate) {
        super(delegate);
    }

    @Override
    public Mono<Void> write(final Publisher<? extends T> publisher, final ResolvableType resolvableType, final MediaType mediaType, final ReactiveHttpOutputMessage reactiveHttpOutputMessage, final Map<String, Object> hints) {
        return getDelegate().write(publisher, resolvableType, mediaType, reactiveHttpOutputMessage, hints);
    }
}

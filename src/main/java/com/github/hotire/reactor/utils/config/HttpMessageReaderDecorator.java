package com.github.hotire.reactor.utils.config;

import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.codec.HttpMessageReader;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


public class HttpMessageReaderDecorator<T> implements HttpMessageReader<T> {

    private final HttpMessageReader<T> delegate;

    public HttpMessageReaderDecorator(final HttpMessageReader<T> delegate) {
        this.delegate = delegate;
    }

    protected HttpMessageReader<T> getDelegate() {
        return delegate;
    }

    @Override
    public List<MediaType> getReadableMediaTypes() {
        return getDelegate().getReadableMediaTypes();
    }

    @Override
    public boolean canRead(final ResolvableType resolvableType, final MediaType mediaType) {
        return getDelegate().canRead(resolvableType, mediaType);
    }

    @Override
    public Mono<T> readMono(final ResolvableType resolvableType, final ReactiveHttpInputMessage reactiveHttpInputMessage, final Map<String, Object> map) {
        return getDelegate().readMono(resolvableType, reactiveHttpInputMessage, map);
    }

    @Override
    public Flux<T> read(final ResolvableType resolvableType, final ReactiveHttpInputMessage reactiveHttpInputMessage, final Map<String, Object> map) {
        return getDelegate().read(resolvableType, reactiveHttpInputMessage, map);
    }
}

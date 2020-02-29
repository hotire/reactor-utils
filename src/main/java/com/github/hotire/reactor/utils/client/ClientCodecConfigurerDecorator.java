package com.github.hotire.reactor.utils.client;

import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;

import java.util.List;

public class ClientCodecConfigurerDecorator implements ClientCodecConfigurer {

    private final ClientCodecConfigurer delegate;

    public ClientCodecConfigurerDecorator(final ClientCodecConfigurer delegate) {
        this.delegate = delegate;
    }

    protected ClientCodecConfigurer getDelegate() {
        return delegate;
    }

    @Override
    public ClientDefaultCodecs defaultCodecs() {
        return getDelegate().defaultCodecs();
    }

    @Override
    public CustomCodecs customCodecs() {
        return getDelegate().customCodecs();
    }

    @Override
    public void registerDefaults(boolean b) {
        getDelegate().registerDefaults(b);
    }

    @Override
    public List<HttpMessageReader<?>> getReaders() {
        return getDelegate().getReaders();
    }

    @Override
    public List<HttpMessageWriter<?>> getWriters() {
        return getDelegate().getWriters();
    }

    @Override
    public ClientCodecConfigurer clone() {
        return getDelegate().clone();
    }
}

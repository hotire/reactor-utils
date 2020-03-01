package com.github.hotire.reactor.utils.client;

import com.github.hotire.reactor.utils.codec.Utf8HttpMessageWriterDecorator;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.HttpMessageWriter;

import java.util.List;
import java.util.stream.Collectors;

public class Utf8ClientCodecConfigurerDecorator extends ClientCodecConfigurerDecorator{
    public Utf8ClientCodecConfigurerDecorator(final ClientCodecConfigurer delegate) {
        super(delegate);
    }

    @Override
    public List<HttpMessageWriter<?>> getWriters() {
        return getDelegate().getWriters().stream().map(Utf8HttpMessageWriterDecorator::new).collect(Collectors.toList());
    }
}

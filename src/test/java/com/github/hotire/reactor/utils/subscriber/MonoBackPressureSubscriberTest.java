package com.github.hotire.reactor.utils.subscriber;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

class MonoBackPressureSubscriberTest {

    @Test
    void hookOnNext() {
        // given
        final String expected = "hello";
        final ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        final Consumer<String> consumer = mock(Consumer.class);
        final MonoBackPressureSubscriber<String> subscriber = new MonoBackPressureSubscriber<>(1, 1, consumer, error -> {}, () -> {});

        // when
        subscriber.hookOnNext(Mono.just(expected));

        // then
        verify(consumer, times(1)).accept(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(expected);
    }
}
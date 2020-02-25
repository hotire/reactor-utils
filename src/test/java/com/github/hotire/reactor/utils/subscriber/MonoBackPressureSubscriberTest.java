package com.github.hotire.reactor.utils.subscriber;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.reactivestreams.Subscription;
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

    @Test
    void hookOnSubscribe() {
        // given
        final MonoBackPressureSubscriber<String> subscriber = MonoBackPressureSubscriber.of(1, 1,  mock(Consumer.class));

        // when
        subscriber.hookOnSubscribe(mock(Subscription.class));

        // no assert
    }

    @Test
    void create() {
        // given
        final Consumer<?> consumer = mock(Consumer.class);
        final Consumer<? super Throwable> doOnError = mock(Consumer.class);
        final Runnable doOncomplete = mock(Runnable.class);

        // when  then
        assertThat(MonoBackPressureSubscriber.of(1, 1, consumer)).isNotNull();
        assertThat(MonoBackPressureSubscriber.of(1, 1, consumer, doOnError)).isNotNull();
        assertThat(MonoBackPressureSubscriber.of(1, 1, consumer, doOnError, doOncomplete)).isNotNull();
    }
}
package com.github.hotire.reactor.utils.mdc;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.Callable;

import static org.mockito.Mockito.*;


class MDCCallableTest {
  @Test
  void call() throws Exception {
    // given
    @SuppressWarnings("unchecked")
    final Callable<String> runnable = mock(Callable.class);
    final MDCCallable<String> mdcRunnable = new MDCCallable<>(runnable, new HashMap<>());

    // when
    mdcRunnable.call();

    // then
    verify(runnable, times(1)).call();
  }
}
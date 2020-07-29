package com.github.hotire.reactor.utils.mdc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static org.mockito.Mockito.*;


class MDCCallableTest {
  @Test
  void call() throws Exception {
    // given
    @SuppressWarnings("unchecked")
    final Callable<String> callable = mock(Callable.class);
    final Map<String, String> contextMap = new HashMap<>();
    contextMap.put("", "");
    final MDCCallable<String> mdcRunnable = new MDCCallable<>(callable, contextMap);

    // when
    mdcRunnable.call();

    // then
    verify(callable, times(1)).call();
  }

  @Test
  void callError() throws Exception {
    // given
    @SuppressWarnings("unchecked")
    final Callable<String> callable = mock(Callable.class);
    final Map<String, String> contextMap = new HashMap<>();
    contextMap.put("", "");
    final MDCCallable<String> mdcRunnable = new MDCCallable<>(callable, contextMap);

    // when
    when(callable.call()).thenThrow(new RuntimeException());

    Assertions.assertThrows(RuntimeException.class, mdcRunnable::call);
  }

}
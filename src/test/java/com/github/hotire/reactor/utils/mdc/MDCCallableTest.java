package com.github.hotire.reactor.utils.mdc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.Callable;
import org.junit.jupiter.api.Test;


class MDCCallableTest {
  @Test
  void call() throws Exception {
    // given
    @SuppressWarnings("unchecked")
    final Callable<String> runnable = mock(Callable.class);
    final Runnable doOnExecute = mock(Runnable.class);
    final Runnable doOnTerminate = mock(Runnable.class);
    final MDCCallable<String> mdcRunnable = new MDCCallable<>(runnable, doOnExecute, doOnTerminate);

    // when
    mdcRunnable.call();

    // then
    verify(runnable, times(1)).call();
    verify(doOnExecute, times(1)).run();
    verify(doOnTerminate, times(1)).run();
  }
}
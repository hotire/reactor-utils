package com.github.hotire.reactor.utils.mdc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;


class MDCRunnableTest {

  @Test
  void run() {
    // given
    final Runnable runnable = mock(Runnable.class);
    final Runnable doOnExecute = mock(Runnable.class);
    final Runnable doOnTerminate = mock(Runnable.class);
    final MDCRunnable mdcRunnable = new MDCRunnable(runnable, doOnExecute, doOnTerminate);

    // when
    mdcRunnable.run();

    // then
    verify(runnable, times(1)).run();
    verify(doOnExecute, times(1)).run();
    verify(doOnTerminate, times(1)).run();
  }
}
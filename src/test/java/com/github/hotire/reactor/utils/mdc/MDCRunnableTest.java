package com.github.hotire.reactor.utils.mdc;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;


class MDCRunnableTest {

  @Test
  void run() {
    // given
    final Runnable runnable = mock(Runnable.class);
    final MDCRunnable mdcRunnable = new MDCRunnable(runnable, new HashMap<>());

    // when
    mdcRunnable.run();

    // then
    verify(runnable, times(1)).run();
  }
}
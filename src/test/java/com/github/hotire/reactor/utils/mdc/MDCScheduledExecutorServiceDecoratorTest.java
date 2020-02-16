package com.github.hotire.reactor.utils.mdc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import org.junit.jupiter.api.Test;


class MDCScheduledExecutorServiceDecoratorTest {

  @Test
  void decorateRunnable() {
    // given
    final MDCScheduledExecutorServiceDecorator service = new MDCScheduledExecutorServiceDecorator(mock(ScheduledExecutorService.class));

    // when
    final Runnable result = service.decorateRunnable(() -> {});

    // then
    assertThat(result).isInstanceOf(MDCRunnable.class);
  }

}
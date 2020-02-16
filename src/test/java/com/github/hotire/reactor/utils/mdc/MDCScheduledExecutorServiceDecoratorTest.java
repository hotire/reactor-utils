package com.github.hotire.reactor.utils.mdc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import org.assertj.core.util.Lists;
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

  @Test
  void decorateCallable() {
   // given
    final MDCScheduledExecutorServiceDecorator service = new MDCScheduledExecutorServiceDecorator(mock(ScheduledExecutorService.class));

    // when
    final Callable<String> result = service.decorateCallable(() -> "s");

    // then
    assertThat(result).isInstanceOf(MDCCallable.class);
  }

  @Test
  void wrapCallableCollection() {
    // given
    final MDCScheduledExecutorServiceDecorator service = new MDCScheduledExecutorServiceDecorator(mock(ScheduledExecutorService.class));

    // when
    final Collection<? extends Callable<String>> result = service.wrapCallableCollection(Lists.newArrayList(() -> "s"));

    // then
    assertThat(result.size()).isEqualTo(1);
    result.forEach(callable -> assertThat(callable).isInstanceOf(MDCCallable.class));
  }

}
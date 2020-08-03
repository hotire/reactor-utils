package com.github.hotire.reactor.utils.mdc;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


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

  @Test
  void schedule() {
    // given
    final long delay = 1L;
    final TimeUnit unit = TimeUnit.SECONDS;
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.schedule(() -> {}, delay, unit);

    // then
    verify(service, times(1)).schedule(any(Runnable.class), eq(delay), eq(unit));
  }

  @Test
  void scheduleCallable() {
    // given
    final long delay = 1L;
    final TimeUnit unit = TimeUnit.SECONDS;
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.schedule(mock(Callable.class), delay, unit);

    // then
    verify(service, times(1)).schedule(any(Callable.class), eq(delay), eq(unit));
  }

  @Test
  void scheduleAtFixedRate() {
    // given
    final long initialDelay = 1L;
    final long period = 1L;
    final TimeUnit unit = TimeUnit.SECONDS;
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.scheduleAtFixedRate(() -> {}, initialDelay, period, unit);

    // then
    verify(service, times(1)).scheduleAtFixedRate(any(), eq(initialDelay), eq(period), eq(unit));
  }

  @Test
  void scheduleWithFixedDelay() {
    // given
    final long initialDelay = 1L;
    final long period = 1L;
    final TimeUnit unit = TimeUnit.SECONDS;
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.scheduleWithFixedDelay(() -> {}, initialDelay, period, unit);

    // then
    verify(service, times(1)).scheduleWithFixedDelay(any(), eq(initialDelay), eq(period), eq(unit));
  }

  @Test
  void shutdown() {
    // given
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.shutdown();

    // then
    verify(service, times(1)).shutdown();
  }

  @Test
  void shutdownNow() {
    // given
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.shutdownNow();

    // then
    verify(service, times(1)).shutdownNow();
  }

  @Test
  void isShutdown() {
    // given
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.isShutdown();

    // then
    verify(service, times(1)).isShutdown();
  }

  @Test
  void isTerminated() {
    // given
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.isTerminated();

    // then
    verify(service, times(1)).isTerminated();

  }

  @Test
  void awaitTermination() throws InterruptedException {
    // given
    final long timeout = 1L;
    final TimeUnit unit = TimeUnit.SECONDS;
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.awaitTermination(timeout, unit);

    // then
    verify(service, times(1)).awaitTermination(timeout, unit);
  }

  @Test
  void submit() {
    // given
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.submit(mock(Callable.class));

    // then
    verify(service, times(1)).submit(any(Callable.class));
  }

  @Test
  void submitRunnableAndResult() {
    // given
    final Object result = mock(Object.class);
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.submit(mock(Runnable.class), result);

    // then
    verify(service, times(1)).submit(any(Runnable.class), eq(result));
  }

  @Test
  void submitRunnable() {
    // given
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.submit(mock(Runnable.class));

    // then
    verify(service, times(1)).submit(any(Runnable.class));
  }

  @Test
  void invokeAll() throws InterruptedException {
    // given
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.invokeAll(mock(Collection.class));

    // then
    verify(service, times(1)).invokeAll(any(Collection.class));
  }

  @Test
  void invokeAllTimeoutAndUnit() throws InterruptedException {
    // given
    final long timeout = 1L;
    final TimeUnit unit = TimeUnit.SECONDS;
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.invokeAll(mock(Collection.class), timeout, unit);

    // then
    verify(service, times(1)).invokeAll(any(Collection.class), eq(timeout), eq(unit));
  }

  @Test
  void invokeAny() throws InterruptedException, ExecutionException {
    // given
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.invokeAny(mock(Collection.class));

    // then
    verify(service, times(1)).invokeAny(any(Collection.class));
  }

  @Test
  void invokeAnyTimeoutAndUnit() throws InterruptedException, ExecutionException, TimeoutException {
    // given
    final long timeout = 1L;
    final TimeUnit unit = TimeUnit.SECONDS;
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.invokeAny(mock(Collection.class), timeout, unit);

    // then
    verify(service, times(1)).invokeAny(any(Collection.class), eq(timeout), eq(unit));
  }

  @Test
  void execute() {
    // given
    final ScheduledExecutorService service = mock(ScheduledExecutorService.class);
    final MDCScheduledExecutorServiceDecorator decorator = new MDCScheduledExecutorServiceDecorator(service);

    // when
    decorator.execute(mock(Runnable.class));

    // then
    verify(service, times(1)).execute(any(Runnable.class));
  }
}
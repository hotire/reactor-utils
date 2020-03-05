package com.github.hotire.reactor.utils.mdc;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;


public class MDCScheduledExecutorServiceDecorator implements ScheduledExecutorService {

  private final ScheduledExecutorService delegate;
  private final Runnable doOnExecute;
  private final Runnable doOnTerminate;

  public MDCScheduledExecutorServiceDecorator(ScheduledExecutorService delegate) {
    this.delegate = delegate;
    final Map<String, String> copyContextMap = MDCUtils.getContextMap();
    this.doOnExecute = () -> copyContextMap.forEach(MDCUtils::put);
    this.doOnTerminate = () -> copyContextMap.forEach((key, value) -> MDCUtils.remove(key));

  }

  protected Runnable decorateRunnable(Runnable runnable) {
    return new MDCRunnable(runnable, doOnExecute, doOnTerminate);
  }

  protected <T> Callable<T> decorateCallable(Callable<T> callable) {
    return new MDCCallable<>(callable, doOnExecute, doOnExecute);
  }

  protected <T> Collection<? extends Callable<T>> wrapCallableCollection(
    Collection<? extends Callable<T>> tasks) {
    return tasks.stream()
                .filter(task -> !(task instanceof MDCCallable))
                .map(this::decorateCallable)
                .collect(toList());
  }

  @Override
  public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
    return getDelegate().schedule(decorateRunnable(command), delay, unit);
  }

  @Override
  public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
    return getDelegate().schedule(decorateCallable(callable), delay, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period,
    TimeUnit unit) {
    return getDelegate().scheduleAtFixedRate(decorateRunnable(command), initialDelay, period, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay,
    TimeUnit unit) {
    return getDelegate().scheduleAtFixedRate(decorateRunnable(command), initialDelay, delay, unit);
  }

  @Override
  public void shutdown() {
    getDelegate().shutdown();
  }

  @Override
  public List<Runnable> shutdownNow() {
    return getDelegate().shutdownNow();
  }

  @Override
  public boolean isShutdown() {
    return getDelegate().isShutdown();
  }

  @Override
  public boolean isTerminated() {
    return getDelegate().isTerminated();
  }

  @Override
  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    return getDelegate().awaitTermination(timeout, unit);
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    return getDelegate().submit(decorateCallable(task));
  }

  @Override
  public <T> Future<T> submit(Runnable task, T result) {
    return getDelegate().submit(decorateRunnable(task), result);
  }

  @Override
  public Future<?> submit(Runnable task) {
    return getDelegate().submit(decorateRunnable(task));
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
    throws InterruptedException {
    return getDelegate().invokeAll(wrapCallableCollection(tasks));
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout,
    TimeUnit unit) throws InterruptedException {
    return getDelegate().invokeAll(wrapCallableCollection(tasks), timeout, unit);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
    throws InterruptedException, ExecutionException {
    return getDelegate().invokeAny(wrapCallableCollection(tasks));
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
    throws InterruptedException, ExecutionException, TimeoutException {
    return getDelegate().invokeAny(wrapCallableCollection(tasks), timeout, unit);
  }

  @Override
  public void execute(Runnable command) {
    getDelegate().execute(decorateRunnable(command));
  }

  protected ScheduledExecutorService getDelegate() {
    return this.delegate;
  }
}

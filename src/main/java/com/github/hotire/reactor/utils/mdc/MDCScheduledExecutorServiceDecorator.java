package com.github.hotire.reactor.utils.mdc;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MDCScheduledExecutorServiceDecorator implements ScheduledExecutorService {

  private final ScheduledExecutorService delegate;

  public MDCScheduledExecutorServiceDecorator(ScheduledExecutorService delegate) {
    this.delegate = delegate;
  }

  @Override
  public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
    return getDelegate().schedule(command, delay, unit);
  }

  @Override
  public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
    return getDelegate().schedule(callable, delay, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period,
    TimeUnit unit) {
    return getDelegate().scheduleAtFixedRate(command, initialDelay, period, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay,
    TimeUnit unit) {
    return getDelegate().scheduleAtFixedRate(command, initialDelay, delay, unit);
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
    return getDelegate().submit(task);
  }

  @Override
  public <T> Future<T> submit(Runnable task, T result) {
    return getDelegate().submit(task, result);
  }

  @Override
  public Future<?> submit(Runnable task) {
    return getDelegate().submit(task);
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
    throws InterruptedException {
    return getDelegate().invokeAll(tasks);
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout,
    TimeUnit unit) throws InterruptedException {
    return getDelegate().invokeAll(tasks, timeout, unit);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
    throws InterruptedException, ExecutionException {
    return getDelegate().invokeAny(tasks);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
    throws InterruptedException, ExecutionException, TimeoutException {
    return getDelegate().invokeAny(tasks, timeout, unit);
  }

  @Override
  public void execute(Runnable command) {
    getDelegate().execute(command);
  }

  protected ScheduledExecutorService getDelegate() {
    return this.delegate;
  }
}

package com.github.hotire.reactor.utils.mdc;

import java.util.concurrent.Callable;


public class MDCCallable<T> implements Callable<T> {

  private final Callable<T> delegate;
  private final Runnable doOnExecute;
  private final Runnable doOnTerminate;

  public MDCCallable(Callable<T> delegate,Runnable doOnExecute, Runnable doOnTerminate) {
    this.delegate = delegate;
    this.doOnExecute = doOnExecute;
    this.doOnTerminate = doOnTerminate;
  }

  @Override
  public T call() throws Exception {
    try {
      doOnExecute.run();
      return delegate.call();
    } finally {
      doOnTerminate.run();
    }
  }
}

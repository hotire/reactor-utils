package com.github.hotire.reactor.utils.mdc;

public class MDCRunnable implements Runnable{

  private final Runnable delegate;
  private final Runnable doOnExecute;
  private final Runnable doOnTerminate;

  public MDCRunnable(Runnable delegate,Runnable doOnExecute, Runnable doOnTerminate) {
    this.delegate = delegate;
    this.doOnExecute = doOnExecute;
    this.doOnTerminate = doOnTerminate;
  }

  @Override
  public void run() {
    try {
      doOnExecute.run();
      delegate.run();
    } finally {
      doOnTerminate.run();
    }
  }
}

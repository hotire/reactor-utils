package com.github.hotire.reactor.utils.mdc;

import java.util.Map;
import java.util.concurrent.Callable;


public class MDCCallable<T> implements Callable<T> {

  private final Callable<T> delegate;
  private final Map<String, String> contextMap;

  public MDCCallable(final Callable<T> delegate,final Map<String, String> contextMap) {
    this.delegate = delegate;
    this.contextMap = contextMap;
  }

  @Override
  public T call() throws Exception {
    try {
      contextMap.forEach(MDCUtils::put);
      return delegate.call();
    } finally {
      contextMap.forEach((key, value) -> MDCUtils.remove(key));
    }
  }
}

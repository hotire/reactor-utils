package com.github.hotire.reactor.utils.mdc;

import java.util.Map;

public class MDCRunnable implements Runnable{

  private final Runnable delegate;
  private final Map<String, String> contextMap;

  public MDCRunnable(final Runnable delegate, final Map<String, String> contextMap) {
    this.delegate = delegate;
    this.contextMap = contextMap;
  }

  @Override
  public void run() {
    try {
      contextMap.forEach(MDCUtils::put);
      delegate.run();
    } finally {
      contextMap.forEach((key, value) -> MDCUtils.remove(key));
    }
  }
}

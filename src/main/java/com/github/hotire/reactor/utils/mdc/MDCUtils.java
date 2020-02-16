package com.github.hotire.reactor.utils.mdc;


import java.util.Map;
import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;


public class MDCUtils {

  private MDCUtils(){}

  private static final MDCAdapter ADAPTER = MDC.getMDCAdapter();

  public static String get(String key) {
    return ADAPTER.get(key);
  }

  public static void put(String key, String value) {
    ADAPTER.put(key, value);
  }

  public static void remove(String key) {
    ADAPTER.remove(key);
  }

  public static void clear() {
    ADAPTER.clear();
  }

  public static Map<String, String> getContextMap() {
    return ADAPTER.getCopyOfContextMap();
  }
}

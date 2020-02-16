package com.github.hotire.reactor.utils.mdc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;


class MDCUtilsTest {

  @Test
  void create()
    // given
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    final Constructor<MDCUtils> constructor = MDCUtils.class.getDeclaredConstructor(null);

    // when then
    assertThrows(IllegalAccessException.class, constructor::newInstance);

    // when
    constructor.setAccessible(true);
    final MDCUtils instance = constructor.newInstance();

    // then
    assertThat(instance).isNotNull();
  }

  @Test
  void get() {
    // when
    MDCUtils.get("key");

    // no assert
  }

  @Test
  void put() {
    // when
    MDCUtils.put("key", "value");

    // no assert
  }

  @Test
  void clear() {
    MDCUtils.clear();
  }

}
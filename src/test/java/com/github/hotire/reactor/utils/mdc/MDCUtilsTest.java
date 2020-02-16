package com.github.hotire.reactor.utils.mdc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Verify MDCUtils of static class")
class MDCUtilsTest {

  @DisplayName("Verify private constructor")
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

  @DisplayName("Verify invoke get")
  @Test
  void get() {
    // when
    MDCUtils.get("key");

    // no assert
  }

  @DisplayName("Verify invoke put")
  @Test
  void put() {
    // when
    MDCUtils.put("key", "value");

    // no assert
  }

  @DisplayName("Verify invoke clear")
  @Test
  void clear() {
    // when
    MDCUtils.clear();

    // no assert
  }

  @DisplayName("Verify invoke remove")
  @Test
  void remove() {
    // when
    MDCUtils.remove("key");

    // no assert
  }

}
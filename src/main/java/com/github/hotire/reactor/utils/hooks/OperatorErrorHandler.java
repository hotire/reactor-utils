package com.github.hotire.reactor.utils.hooks;

import java.util.function.BiFunction;

public interface OperatorErrorHandler extends BiFunction<Throwable, Object, Throwable> {
}

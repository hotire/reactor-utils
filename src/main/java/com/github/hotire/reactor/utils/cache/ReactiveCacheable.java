package com.github.hotire.reactor.utils.cache;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReactiveCacheable {
    String name();
    String key() default "";
}

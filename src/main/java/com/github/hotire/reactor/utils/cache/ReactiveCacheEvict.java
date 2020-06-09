package com.github.hotire.reactor.utils.cache;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReactiveCacheEvict {
    String name();
    String key() default "";
}

package com.github.hotire.reactor.utils.bind.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {

  String fieldName();

  String message() default "";

  String errorCode() default "";

}

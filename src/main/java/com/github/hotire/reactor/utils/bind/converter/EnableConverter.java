package com.github.hotire.reactor.utils.bind.converter;

import com.github.hotire.reactor.utils.bind.converter.beanutils.EnableBeanUtilsConverter;
import com.github.hotire.reactor.utils.bind.converter.spring.EnableSpringConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@EnableBeanUtilsConverter
@EnableSpringConverter
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableConverter {
}

package com.github.hotire.reactor.utils.bind;


import com.github.hotire.reactor.utils.bind.converter.beanutils.*;
import com.github.hotire.reactor.utils.bind.converter.spring.BooleanConverter;
import com.github.hotire.reactor.utils.bind.converter.spring.LongConverter;
import com.github.hotire.reactor.utils.bind.validation.BindingResultException;
import com.github.hotire.reactor.utils.bind.validation.ConstraintValidator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class BindingUtils {

  private static final ConstraintValidator VALIDATOR;

  private static final ConversionService CONVERTER;

  private BindingUtils() {}

  static {
    ConvertUtils.register(new LocalDateConverter(), LocalDate.class);
    ConvertUtils.register(new LocalDateTimeConverter(), LocalDateTime.class);
    ConvertUtils.register(new MonthConverter(), Month.class);
    ConvertUtils.register(new YearConverter(), Year.class);
    ConvertUtils.register(new OffsetDateTimeConverter(), OffsetDateTime.class);
    ConvertUtils.register(new InstantConverter(), Instant.class);

    final GenericConversionService conversionService = new GenericConversionService();
    conversionService.addConverter(new LongConverter());
    conversionService.addConverter(new BooleanConverter());
    conversionService.addConverter(new com.github.hotire.reactor.utils.bind.converter.spring.OffsetDateTimeConverter());
    conversionService.addConverter(new com.github.hotire.reactor.utils.bind.converter.spring.InstantConverter());

    final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
    localValidatorFactoryBean.afterPropertiesSet();

    CONVERTER = conversionService;
    VALIDATOR = new ConstraintValidator(localValidatorFactoryBean);
  }

  public static <T> T bind(final ServerRequest request, final Class<T> type) {
    return bind(request, type, true);
  }

  public static <T> T bind(final ServerRequest request, final Class<T> type, boolean isValidation) {
    try {
      final T instance = type.getConstructor().newInstance();

      final Map<String, String> queryParams = new HashMap<>();
      request.queryParams().forEach((s, strings) -> queryParams.put(s, strings.get(0)));

      BeanUtils.populate(instance, queryParams);
      BeanUtils.populate(instance, request.pathVariables());

      if (isValidation) {
        validate(instance, true);
      }

      return instance;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> Mono<T> bindToMono(final ServerRequest request, final Class<T> type) {
    return bindToMono(request, type, true);
  }

  public static <T> Mono<T> bindToMono(final ServerRequest request, final Class<T> type, final boolean isValidation) {
    return Mono.create(monoSink -> monoSink.success(bind(request, type, isValidation)));
  }

  public static <T> Optional<T> bindOne(final ServerRequest request, final Class<T> type) {
    if (!CONVERTER.canConvert(String.class, type)) {
      throw new IllegalArgumentException("Cannot convert type : " + type);
    }
    final AtomicReference<T> atomicReference = new AtomicReference<>();
    request.queryParams().forEach((key, values) -> atomicReference.set(CONVERTER.convert(values.get(0), type)));
    request.pathVariables().forEach((key, value) -> atomicReference.set(CONVERTER.convert(value, type)));

    return Optional.ofNullable(atomicReference.get());
  }

  public static <T> Mono<T> bindOneToMono(final ServerRequest request, final Class<T> type) {
    return Mono.create(monoSink -> {
      bindOne(request, type).ifPresent(monoSink::success);
      monoSink.success();
    });
  }

  public static BeanPropertyBindingResult validate(final Object target, final boolean isThrowable) {
    final BeanPropertyBindingResult errors = new BeanPropertyBindingResult(target, target.getClass().getSimpleName());
    VALIDATOR.validate(target, errors);
    if (errors.hasErrors() && isThrowable) {
      throw new BindingResultException(errors);
    }
     return errors;
  }

}

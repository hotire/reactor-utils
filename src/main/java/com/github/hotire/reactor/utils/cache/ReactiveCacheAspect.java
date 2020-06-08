package com.github.hotire.reactor.utils.cache;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.ResolvableType;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Aspect
@RequiredArgsConstructor
public class ReactiveCacheAspect {

    private final ReactiveCacheManager reactiveCacheManager;

    @SuppressWarnings("unchecked")
    @Around("@annotation(reactiveCacheable)")
    public Object around(final ProceedingJoinPoint joinPoint, final ReactiveCacheable reactiveCacheable) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String key = parseSpel(methodSignature.getParameterNames(), joinPoint.getArgs(), reactiveCacheable.key());

        final Method method = methodSignature.getMethod();
        final ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
        final Type returnTypeInsideMono = parameterizedType.getActualTypeArguments()[0];
        final Class<?> returnClass = ResolvableType.forType(returnTypeInsideMono).resolve();
        final Supplier retriever = () -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };

        return reactiveCacheManager.cacheMono(reactiveCacheable.name(), key, retriever, returnClass);
    }


    protected String parseSpel(final String[] params, final Object[] arguments, final String spel) {
        final EvaluationContext context = new StandardEvaluationContext();

        IntStream.range(0, params.length)
                 .forEach(index ->  context.setVariable(params[index], arguments[index]));

        return new SpelExpressionParser().parseExpression(spel).getValue(context, String.class);
    }

    protected String generateKey(final Object... objects) {
        return Arrays.stream(objects)
                     .map(obj -> Optional.ofNullable(obj)
                                         .map(Object::toString)
                                         .orElse(null))
                     .collect(Collectors.joining(":"));
    }
}

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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@Aspect
@RequiredArgsConstructor
public class ReactiveCacheAspect {

    private final ReactiveCacheManager reactiveCacheManager;

    @SuppressWarnings("unchecked")
    @Around("@annotation(reactiveCacheable)")
    public Object cache(final ProceedingJoinPoint joinPoint, final ReactiveCacheable reactiveCacheable) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String key = parseSpel(methodSignature.getParameterNames(), joinPoint.getArgs(), reactiveCacheable.key());

        final Supplier<Class<?>> returnClass = () -> {
            final Method method = methodSignature.getMethod();
            final ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
            final Type returnTypeInsideMono = parameterizedType.getActualTypeArguments()[0];
            return ResolvableType.forType(returnTypeInsideMono).resolve();
        };

        if (Mono.class.equals(methodSignature.getReturnType())) {
            return reactiveCacheManager.cacheMono(reactiveCacheable.name(), key, retriever(joinPoint), returnClass.get());
        }

        if (Flux.class.equals(methodSignature.getReturnType())) {
            return reactiveCacheManager.cacheFlux(reactiveCacheable.name(), key, retriever(joinPoint), returnClass.get());
        }

        return joinPoint.proceed();
    }

    @SuppressWarnings("unchecked")
    @Around("@annotation(reactiveCacheEvict)")
    public Object evict(final ProceedingJoinPoint joinPoint, final ReactiveCacheEvict reactiveCacheEvict) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String key = parseSpel(methodSignature.getParameterNames(), joinPoint.getArgs(), reactiveCacheEvict.key());
        final Method method = methodSignature.getMethod();

        if (Mono.class.equals(method.getReturnType())) {
            return reactiveCacheManager.evict(reactiveCacheEvict.name(), key)
                                       .then((Mono<?>)retriever(joinPoint).get());
        }

        if (Flux.class.equals(method.getReturnType())) {
            return reactiveCacheManager.evict(reactiveCacheEvict.name(), key)
                                       .thenMany((Flux<?>)retriever(joinPoint).get());
        }

        return reactiveCacheManager.evict(reactiveCacheEvict.name(), key)
                                   .then((Mono.just(retriever(joinPoint).get())));
    }

    protected String parseSpel(final String[] params, final Object[] arguments, final String spel) {
        final EvaluationContext context = new StandardEvaluationContext();

        IntStream.range(0, params.length)
                 .forEach(index ->  context.setVariable(params[index], arguments[index]));

        return new SpelExpressionParser().parseExpression(spel).getValue(context, String.class);
    }

    @SuppressWarnings("rawtypes")
    protected Supplier retriever(final ProceedingJoinPoint joinPoint) {
        return () -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

}

package com.github.hotire.reactor.utils.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Aspect
public class ReactiveCacheAspect {

    private final ReactiveCacheManager reactiveCacheManager;

    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();


    public ReactiveCacheAspect(ReactiveCacheManager reactiveCacheManager) {
        this.reactiveCacheManager = reactiveCacheManager;
    }

    @SuppressWarnings("unchecked")
    @Around("@annotation(reactiveCacheable)")
    public Object around(final ProceedingJoinPoint joinPoint, final ReactiveCacheable reactiveCacheable) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String key = parseSpel(methodSignature.getParameterNames(), joinPoint.getArgs(), reactiveCacheable.key());

        return reactiveCacheManager.cacheMono(reactiveCacheable.name(), key, methodSignature.getReturnType());
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

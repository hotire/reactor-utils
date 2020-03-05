package com.github.hotire.reactor.utils.hooks;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultOperatorErrorHandler implements OperatorErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultOperatorErrorHandler.class);

    @Override
    public Throwable apply(Throwable throwable, Object o) {
        log.error("arg : {}", o);
        return throwable;
    }
}

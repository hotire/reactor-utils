package com.github.hotire.reactor.utils.hooks;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggingOperatorErrorHandler implements OperatorErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(LoggingOperatorErrorHandler.class);

    @Override
    public Throwable apply(Throwable error, Object arg) {
        log.error("error : {}, arg : {}", error, arg);
        return error;
    }
}

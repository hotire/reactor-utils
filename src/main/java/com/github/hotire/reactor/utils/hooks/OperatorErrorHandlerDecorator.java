package com.github.hotire.reactor.utils.hooks;

public class OperatorErrorHandlerDecorator implements OperatorErrorHandler {

    private final OperatorErrorHandler delegate;

    public OperatorErrorHandlerDecorator(final OperatorErrorHandler delegate) {
        this.delegate = delegate;
    }

    protected OperatorErrorHandler getDelegate() {
        return delegate;
    }

    @Override
    public Throwable apply(final Throwable throwable, final Object o) {
        return getDelegate().apply(throwable, o);
    }
}

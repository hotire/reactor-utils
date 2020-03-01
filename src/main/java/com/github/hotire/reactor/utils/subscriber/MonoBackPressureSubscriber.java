package com.github.hotire.reactor.utils.subscriber;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public class MonoBackPressureSubscriber<T> extends BaseSubscriber<Mono<T>> {

  private final Integer initRequest;

  private final Integer rateRequest;

  private final Consumer<T> consumer;

  private final Consumer<? super Throwable> doOnError;

  private final Runnable doOnSuccess;

  MonoBackPressureSubscriber(Integer initRequest, Integer rateRequest,
          Consumer<T> consumer, Consumer<? super Throwable> doOnError,
          Runnable doOnSuccess) {
    this.initRequest = initRequest;
    this.rateRequest = rateRequest;
    this.consumer = consumer;
    this.doOnError = doOnError;
    this.doOnSuccess = doOnSuccess;
  }

  public static<T> MonoBackPressureSubscriber<T> of(Integer initRequest, Integer rateRequest, Consumer<T> consumer) {
    return new MonoBackPressureSubscriber<>(initRequest, rateRequest, consumer, t -> {}, () -> {});
  }

  public static<T> MonoBackPressureSubscriber<T> of(Integer initRequest, Integer rateRequest,
    Consumer<T> consumer, Consumer<? super Throwable> doOnError) {
    return new MonoBackPressureSubscriber<>(initRequest, rateRequest, consumer, doOnError, ()-> {});
  }

  public static<T> MonoBackPressureSubscriber<T> of(Integer initRequest, Integer rateRequest,
    Consumer<T> consumer, Consumer<? super Throwable> doOnError, Runnable doOnSuccess) {
    return new MonoBackPressureSubscriber<>(initRequest, rateRequest, consumer, doOnError, doOnSuccess);
  }

  @Override
  protected void hookOnSubscribe(Subscription subscription) {
    request(initRequest);
  }

  @Override
  protected void hookOnNext(Mono<T> mono) {
    mono.subscribe(consumer,
      error -> {
        doOnError.accept(error);
        request(rateRequest);
      },
      () -> {
        doOnSuccess.run();
        request(rateRequest);
      });
  }
}

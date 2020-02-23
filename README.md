# Reactor Utils

![toaster](/doc/toaster.png)

> This is not bread-utils, just the toaster

## Installation

### Maven 
```xml
<repository>
  <id>hotire</id>
  <url>http://dl.bintray.com/hotire/utils</url>
</repository>

<dependency>
    <groupId>com.github.hotire</groupId>
    <artifactId>reactor-utils</artifactId>
    <version>0.0.4</version>
</dependency>
```

## BindingUtils

> commons-beanutils-based, springframework-core-convert-based

ServerRequest

- queryParams

- pathVariables

bind to instance of class<T> type

**Custom Convert**

- commons-beanutils
  - LocalDateTime.class
  - LocalDate.class
  - Year.class
  - Month.class

- springframework-core-convert-based
  - Long.class
  - Boolean.class


### bind

```java
 public Mono<ServerResponse> handle(ServerRequest request) {
   Data data = bind(request, Data.class)
   ....
 }   
```
: All values of ServerRequest(queryParams, pathVariables) into ValueObject


### bindToMono
~~~java
  public Mono<ServerResponse> handle(ServerRequest request) {
    return bindToMono(request, Data.class)
      .filter(data -> {...})
      .map(data -> {...})
      .doOnError(error -> {...})
  }   
~~~
: All values of ServerRequest(queryParams, pathVariables) into Mono&#60;ValueObject&#62;

### bindOne
```java
  public Mono<ServerResponse> handle(ServerRequest request) {
    final String userId = bindOne(request, String.class).orElse(DEFAULT);
    ....
    ....
  }  
```
: One value of ServerRequest(queryParam, pathVariable) into Optional&#60;ValueObject&#62;

### bindOneToMono
```java
  public Mono<ServerResponse> handle(ServerRequest request) {
    return bindOneToMono(request, String.class)
      .map(userId -> userId.orElseThrow() -> {...})         
      .filter(userId -> {...})
      .map(userId -> {...})
      .doOnError(userId -> {...})
  }   
```
: One value of ServerRequest(queryParam, pathVariable) into Mono&#60;Optional&#60;ValueObject&#62;&#62;

## MonoBackPressureSubscriber

MonoBackPressureSubscriber request to publisher and consume Mono then pull Mono from publisher

```
```

blog : https://blog.naver.com/gngh0101

reference : https://projectreactor.io/docs/core/release/reference/


## MDC

Thread-local based MDC is difficult when you switch thread in Webflux.

If you want to maintain MDC, similarly the thread, MDC must also switch.


### How to use 

```java
class Configuration {
  static {
    Schedulers.addExecutorServiceDecorator(MDCScheduledExecutorServiceDecorator.class.getName(),
          (scheduler, scheduledExecutorService) -> new MDCScheduledExecutorServiceDecorator(scheduledExecutorService));
  }
}
``` 


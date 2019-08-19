# Reactor Utils

![toaster](/doc/toaster.png)

> This is not bread-utils, This is toaster

## Installation

### Maven 
```
<dependency>
    <groupId>com.github.hotire</groupId>
    <artifactId>reactor-utils</artifactId>
    <version>0.0.1</version>
</dependency>
```

## BindingUtils

> commons-beanutils-based, springframework-core-convert-based

ServerRequest

- queryParams

- pathVariables

bind to instance of class<T> type

**Custom Convert**

1.commons-beanutils

- LocalDate
- Year
- Month

2.springframework-core-convert-based
- Long
- Boolean


### bind

```
 public Mono<ServerResponse> handle(ServerRequest request) {
   Data data = bind(request, Data.class)
   ....
 }   
```
: All values of ServerRequest(queryParams, pathVariables) into ValueObject


### bindToMono
~~~
  public Mono<ServerResponse> handle(ServerRequest request) {
    return bindToMono(request, Data.class)
      .filter(data -> {...})
      .map(data -> {...})
      .doOnError(error -> {...})
  }   
~~~
: All values of ServerRequest(queryParams, pathVariables) into Mono&#60;ValueObject&#62;

### bindOne
```
```
### bindOneToMono
```
```

## MonoBackPressureSubscriber

MonoBackPressureSubscriber request to publisher and consume Mono then pull Mono from publisher

```
```

blog : https://blog.naver.com/gngh0101

reference : https://projectreactor.io/docs/core/release/reference/

# Reactor Utils

![toaster](/doc/toaster.png)

> This is not bread-utils, This is toaster

## BindingUtils


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


## MonoBackPressureSubscriber

MonoBackPressureSubscriber request to publisher and consume Mono then pull Mono from publisher

```
```

blog : https://blog.naver.com/gngh0101

reference : https://projectreactor.io/docs/core/release/reference/

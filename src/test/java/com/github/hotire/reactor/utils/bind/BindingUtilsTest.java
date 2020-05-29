package com.github.hotire.reactor.utils.bind;

import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class BindingUtilsTest {

  @Test
  void queryParams() {
    // Given
    final ServerRequest request = mock(ServerRequest.class);
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.put("name", Collections.singletonList("hello"));
    map.put("date", Collections.singletonList("2019-01-01"));
    map.put("year", Collections.singletonList("2019"));
    map.put("month", Collections.singletonList("1"));

    // When
    when(request.queryParams()).thenReturn(map);
    final Mono<Data> dataMono = BindingUtils.bindToMono(request, Data.class).log("data");

    // Then
    StepVerifier.create(dataMono).consumeNextWith(data -> {
      assertThat(data.getName()).isEqualTo("hello");
      assertThat(data.getDate()).isEqualTo(LocalDate.of(2019,1,1));
      assertThat(data.getYear()).isEqualTo(Year.of(2019));
      assertThat(data.getMonth()).isEqualTo(Month.of(1));
    }).verifyComplete();
  }

  @Test
  void pathVariables() {
    // Given
    final ServerRequest request = mock(ServerRequest.class);
    final Map<String, String> map = new HashMap<>();
    map.put("name", "hello");
    map.put("date", "2019-01-02");
    map.put("year", "2018");
    map.put("month", "1");

    // When
    when(request.pathVariables()).thenReturn(map);
    when(request.queryParams()).thenReturn(new LinkedMultiValueMap<>());
    Mono<Data> dataMono = BindingUtils.bindToMono(request, Data.class);

    // Then
    StepVerifier.create(dataMono).consumeNextWith(data -> {
      assertThat(data.getName()).isEqualTo("hello");
      assertThat(data.getDate()).isEqualTo(LocalDate.of(2019,1,2));
      assertThat(data.getYear()).isEqualTo(Year.of(2018));
      assertThat(data.getMonth()).isEqualTo(Month.of(1));
    }).verifyComplete();
  }

  @Test
  void pathVariable() throws InterruptedException {
    // Given
    final ServerRequest request = mock(ServerRequest.class);
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.put("name", Collections.singletonList("hello"));


    // When
    when(request.pathVariables()).thenReturn(new HashMap<>());
    when(request.queryParams()).thenReturn(map);
    final String result = BindingUtils.bindOne(request, String.class).orElse(null);

    // Then
    assertThat(result).isEqualTo("hello");
  }

  @Test
  void queryParam() throws InterruptedException {
    // Given
    final ServerRequest request = mock(ServerRequest.class);
    final Map<String, String> map = new HashMap<>();
    map.put("name", "hello");

    // When
    when(request.pathVariables()).thenReturn(map);
    when(request.queryParams()).thenReturn(new LinkedMultiValueMap<>());
    final String result = BindingUtils.bindOne(request, String.class).orElse(null);

    // Then
    assertThat(result).isEqualTo("hello");
  }

  @Test
  void queryParam_boolean() {
    // Given
    final ServerRequest request = mock(ServerRequest.class);
    final Map<String, String> map = new HashMap<>();
    map.put("test", "true");

    // When
    when(request.pathVariables()).thenReturn(map);
    when(request.queryParams()).thenReturn(new LinkedMultiValueMap<>());
    final Boolean result = BindingUtils.bindOne(request, Boolean.class).orElse(null);

    // Then
    assertThat(result).isEqualTo(true);
  }

  @Test
  void bindOneToMono(){
    // Given
    final ServerRequest request = mock(ServerRequest.class);
    final Map<String, String> map = new HashMap<>();
    map.put("test", "true");

    // When
    when(request.pathVariables()).thenReturn(map);
    when(request.queryParams()).thenReturn(new LinkedMultiValueMap<>());
    final Mono<Boolean> result = BindingUtils.bindOneToMono(request, Boolean.class);

    // Then
    StepVerifier.create(result).expectNext(true).verifyComplete();
  }

  public static class Data {
    private String name;
    private LocalDate date;
    private Year year;
    private Month month;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public LocalDate getDate() {
      return date;
    }

    public void setDate(LocalDate date) {
      this.date = date;
    }

    public Year getYear() {
      return year;
    }

    public void setYear(Year year) {
      this.year = year;
    }

    public Month getMonth() {
      return month;
    }

    public void setMonth(Month month) {
      this.month = month;
    }
  }

}
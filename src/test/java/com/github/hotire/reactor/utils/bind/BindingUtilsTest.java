package com.github.hotire.reactor.utils.bind;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class BindingUtilsTest {

  @Mock
  private ServerRequest request;

  @Test
  public void queryParams() {
    // Given
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.put("name", Collections.singletonList("hello"));
    map.put("date", Collections.singletonList("2019-01-01"));
    map.put("year", Collections.singletonList("2019"));
    map.put("month", Collections.singletonList("1"));

    // When
    when(request.queryParams()).thenReturn(map);
    Mono<Data> dataMono = BindingUtils.bindToMono(request, Data.class).log("data");

    // Then
    StepVerifier.create(dataMono).consumeNextWith(data -> {
      Assertions.assertThat(data.getName()).isEqualTo("hello");
      Assertions.assertThat(data.getDate()).isEqualTo(LocalDate.of(2019,1,1));
      Assertions.assertThat(data.getYear()).isEqualTo(Year.of(2019));
      Assertions.assertThat(data.getMonth()).isEqualTo(Month.of(1));
    }).verifyComplete();
  }

  @Test
  public void pathVariables() {
    // Given
    Map<String, String> map = new HashMap<>();
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
      Assertions.assertThat(data.getName()).isEqualTo("hello");
      Assertions.assertThat(data.getDate()).isEqualTo(LocalDate.of(2019,1,2));
      Assertions.assertThat(data.getYear()).isEqualTo(Year.of(2018));
      Assertions.assertThat(data.getMonth()).isEqualTo(Month.of(1));
    }).verifyComplete();
  }

  @Test
  public void pathVariable() throws InterruptedException {
    // Given
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.put("name", Collections.singletonList("hello"));


    // When
    when(request.pathVariables()).thenReturn(new HashMap<>());
    when(request.queryParams()).thenReturn(map);
    String result = BindingUtils.bindOne(request, String.class);

    // Then
    Assertions.assertThat(result).isEqualTo("hello");
  }

  @Test
  public void queryParam() throws InterruptedException {
    // Given
    Map<String, String> map = new HashMap<>();
    map.put("name", "hello");

    // When
    when(request.pathVariables()).thenReturn(map);
    when(request.queryParams()).thenReturn(new LinkedMultiValueMap<>());
    String result = BindingUtils.bindOne(request, String.class);

    // Then
    Assertions.assertThat(result).isEqualTo("hello");
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
package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;


@WebFluxTest(UserHandler.class)
@RunWith(SpringRunner.class)
public class UserHandlerTest {

  @MockBean
  private UserRepository userRepository;

  @Autowired
  private WebTestClient client;


  @Test
  public void list() {
    when(userRepository.findAll()).thenReturn(Flux.fromIterable(Arrays.asList(new User(), new User())));

    client.get().uri("/user").exchange()
            .expectStatus().isOk()
            .expectBodyList(User.class).hasSize(2);
  }

  @Test
  public void findById() {
    User result = new User("1", "test");
    when(userRepository.findById(anyString())).thenReturn(Mono.just(result));

    client.get().uri("/user/{id}", "1").exchange()
            .expectStatus().isOk()
            .expectBody(User.class).isEqualTo(result)
    ;
  }

  @Test
  public void save() {
    User user = new User();
    when(userRepository.save(any())).thenReturn(Mono.just(user));

    client.post().uri("/user").contentType(APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(user)).exchange()
            .expectStatus().isOk()
            .expectBody(User.class).isEqualTo(user)
    ;
  }

  @Test
  public void update() {
    User user = new User("1", "test");
    when(userRepository.findById(anyString())).thenReturn(Mono.just(user));
    when(userRepository.save(any())).thenReturn(Mono.just(user));

    client.put().uri("/user").contentType(APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(user)).exchange()
            .expectStatus().isOk()
            .expectBody(User.class).isEqualTo(user)
    ;
  }

  @Test
  public void delete() {
    when(userRepository.findById(anyString())).thenReturn(Mono.just(new User()));
    when(userRepository.deleteById(anyString())).thenReturn(Mono.empty());

    client.delete().uri("/user/{id}", "1").exchange()
            .expectStatus().isOk()
            .expectBody().isEmpty()
    ;
  }

  @Test
  public void duplicateKeyException() {
    User user = new User();
    when(userRepository.save(any())).thenThrow(new DuplicateKeyException("test"));

    client.post().uri("/user").contentType(APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(user)).exchange()
            .expectStatus().isBadRequest()
    ;
  }

  @Test
  public void illegalArgumentException() {

  }

  @Test
  public void notFoundException() {
    when(userRepository.findById(anyString())).thenReturn(Mono.empty());

    client.delete().uri("/user/{id}", "1").exchange()
            .expectStatus().isNotFound()
    ;
  }
}
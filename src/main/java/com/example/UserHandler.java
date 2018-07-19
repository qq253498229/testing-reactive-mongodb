package com.example;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;


/**
 * @author wangbin
 */
@RestController
@RequestMapping("/user")
public class UserHandler {
  private UserRepository userRepository;

  public UserHandler(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * 查询列表
   */
  @GetMapping
  public Flux<User> list() {
    return userRepository.findAll();
  }

  /**
   * 通过id查询
   */
  @GetMapping("/{id}")
  public Mono<User> findById(@PathVariable("id") String id) {
    return userRepository.findById(id);
  }

  /**
   * 新建
   */
  @PostMapping
  public Mono<User> save(@RequestBody User user) {
    return userRepository.save(user);
  }

  /**
   * 更新
   */
  @PutMapping
  public Mono<User> update(@RequestBody User user) {
    return userRepository.findById(user.getId())
            .flatMap(old -> userRepository.save(user))
            .switchIfEmpty(Mono.error(new UserNotExistException()));
  }

  /**
   * 通过id删除
   */
  @DeleteMapping("/{id}")
  public Mono<Void> delete(@PathVariable("id") String id) {
    return userRepository.findById(id)
            .switchIfEmpty(Mono.error(new UserNotExistException()))
            .flatMap(old -> userRepository.deleteById(id))
            ;
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity duplicateKeyException() {
    return badRequest().body("用户名已存在");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity illegalArgumentException() {
    return badRequest().body("参数异常");
  }

  @ExceptionHandler(UserNotExistException.class)
  public ResponseEntity notFoundException() {
    return notFound().build();
  }
}

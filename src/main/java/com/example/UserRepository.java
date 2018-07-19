package com.example;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author wangbin
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
  /**
   * 通过姓名查询用户
   *
   * @param username
   * @return
   */
  Mono<User> findByUsername(String username);
}

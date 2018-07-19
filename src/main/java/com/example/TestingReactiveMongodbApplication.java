package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangbin
 */
@SpringBootApplication
public class TestingReactiveMongodbApplication {

  public static void main(String[] args) {
    SpringApplication.run(TestingReactiveMongodbApplication.class, args);
  }


  /*@Bean
  public RouterFunction<?> router(
          UserHandler userHandler
  ) {
    return route(GET("/user"), userHandler::list)
            .andRoute(GET("/user/{id}"), userHandler::findById)
            .andRoute(POST("/user"), userHandler::save)
            .andRoute(PUT("/user/{id}"), userHandler::update)
            .andRoute(DELETE("/user/{id}"), userHandler::delete)
            ;
  }*/
}

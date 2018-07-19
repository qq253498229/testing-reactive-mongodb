package com.example;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author wangbin
 */
@ResponseStatus(BAD_REQUEST)
public class UserExistException extends RuntimeException {
}

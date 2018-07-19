package com.example;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * 用户不存在异常
 *
 * @author wangbin
 */
@ResponseStatus(NOT_FOUND)
public class UserNotExistException extends RuntimeException {
}

package org.laiyw.act.seven.exception;

import lombok.Getter;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/13 11:05
 * @Description TODO
 */
public class InvalidTokenException extends RuntimeException {

    @Getter
    private String token;

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, String token) {
        super(message);
        this.token = token;
    }
}

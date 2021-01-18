package org.laiyw.act.seven.exception;

import org.apache.commons.lang3.StringUtils;
import org.laiyw.act.seven.model.ResponseConstants;
import org.laiyw.act.seven.model.ResponseInfo;
import org.laiyw.act.seven.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/12 15:14
 * @Description TODO
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private RedisUtils redisUtils;

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseInfo authenticationException(AuthenticationException e) {
        return ResponseInfo.fail(ResponseConstants.BAD_REQUEST_CODE, e.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseInfo invalidTokenException(InvalidTokenException e) {
        if (StringUtils.isNotEmpty(e.getToken())) {
            redisUtils.hashDelete(e.getToken());
        }
        return ResponseInfo.fail(ResponseConstants.UNAUTHORIZED_CODE, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseInfo exception(Exception e) {
        return ResponseInfo.fail(ResponseConstants.ERROR_CODE, e.getMessage());
    }

}

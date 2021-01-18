package org.laiyw.act.seven.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/12 10:14
 * @Description TODO
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInfo extends ResponseConstants implements Serializable {

    private String timestamp = DateFormatUtils.format(new Date(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.getPattern());
    private String code;
    private String message;
    private Object payload;

    public static ResponseInfo success() {
        return new ResponseInfo(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static ResponseInfo success(Object payload) {
        return new ResponseInfo(payload);
    }

    public static ResponseInfo success(String message, Object payload) {
        return new ResponseInfo(SUCCESS_CODE, message, payload);
    }

    public static ResponseInfo success(String code, String message, Object payload) {
        return new ResponseInfo(code, message, payload);
    }

    public static ResponseInfo fail() {
        return new ResponseInfo(FAIL_CODE, FAIL_MESSAGE);
    }

    public static ResponseInfo fail(String message) {
        return new ResponseInfo(FAIL_CODE, message);
    }

    public static ResponseInfo fail(String code, String message) {
        return new ResponseInfo(code, message);
    }

    private ResponseInfo(Object payload) {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
        this.payload = payload;
    }

    private ResponseInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseInfo(String code, String message, Object payload) {
        this.code = code;
        this.message = message;
        this.payload = payload;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}

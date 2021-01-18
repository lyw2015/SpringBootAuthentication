package org.laiyw.act.seven.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/13 17:34
 * @Description TODO
 */
@Component
public class RedisUtils {

    private static final String TOKEN_KEY = "access-token";
    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    public void hashSet(String token, Object value) {
        hashOperations.put(TOKEN_KEY, token, value);
    }

    public Object hashGet(String token) {
        return hashOperations.get(TOKEN_KEY, token);
    }

    public void hashDelete(String token) {
        if (hashOperations.hasKey(TOKEN_KEY, token)) {
            hashOperations.delete(TOKEN_KEY, token);
        }
    }
}

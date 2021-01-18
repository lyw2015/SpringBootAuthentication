package org.laiyw.act.seven.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/13 9:23
 * @Description TODO
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.security")
public class JwtSecurityProperties {

    /**
     * Request Headers ： Authorization
     */
    private String authKey;

    private String claimsName;

    /**
     * 令牌前缀，最后留个空格 Bearer
     */
    private String tokenPrefix;

    /**
     * 密钥
     */
    private String secret;

    /**
     * 令牌过期时间(毫秒)
     */
    private Long validityInSeconds;

    /**
     * 返回令牌前缀
     */
    public String getTokenPrefix() {
        return tokenPrefix + " ";
    }
}

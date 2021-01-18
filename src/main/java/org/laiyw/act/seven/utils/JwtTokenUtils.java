package org.laiyw.act.seven.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.laiyw.act.seven.config.JwtSecurityProperties;
import org.laiyw.act.seven.utils.IpAddressUtils;
import org.laiyw.act.seven.utils.MacAddressUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/13 9:17
 * @Description TODO
 */
@Slf4j
@Component
public class JwtTokenUtils {

    private static final String USERNAME_KEY = "userName";
    private static final String CLIENT_IP_KEY = "clientIp";
    private static final String CLIENT_MAC_KEY = "clientMac";
    private JwtSecurityProperties jwtConfig;
    private SecretKey secretKey;

    public JwtTokenUtils(JwtSecurityProperties jwtSecurityProperties) {
        jwtConfig = jwtSecurityProperties;
        secretKey = new SecretKeySpec(Base64.getMimeDecoder().decode(jwtConfig.getSecret()), "AES");
    }

    public JwtSecurityProperties getJwtConfig() {
        return jwtConfig;
    }

    public String createToken(Map<String, Object> claims) {
        Date date = new Date();
        String compact = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .claim(jwtConfig.getClaimsName(), claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击
                .setId(UUID.randomUUID().toString())
                // 签发时间
                .setIssuedAt(date)
                // 过期时间
                .setExpiration(new Date(date.getTime() + jwtConfig.getValidityInSeconds()))
                // 设置签名算法和秘钥
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compressWith(CompressionCodecs.DEFLATE)
                .compact();
        return jwtConfig.getTokenPrefix() + compact;
    }

    public String createToken(String userName) {
        return createToken(new HashMap<String, Object>(1) {{
            put(USERNAME_KEY, userName);
        }});
    }

    /**
     * 写入客户端IP和MAC地址到私有属性
     *
     * @param userName
     * @param request
     * @return
     */
    public String createSecurityToken(final String userName, HttpServletRequest request) {
        final String ipAddress = IpAddressUtils.getIpAddr(request);
        return createToken(new HashMap<String, Object>(3) {{
            put(USERNAME_KEY, userName);
            put(CLIENT_IP_KEY, ipAddress);
            put(CLIENT_MAC_KEY, MacAddressUtils.getMacAddress(ipAddress));
        }});
    }

    /**
     * 校验Token的有效性，并且增加客户端IP、MCAC地址验证
     *
     * @param token
     * @param request
     * @return
     */
    public String verifySecurityGetUserName(String token, HttpServletRequest request) {
        Map map = verifyGetMap(token);
        if (null == map) {
            return null;
        }
        String ipAddress = IpAddressUtils.getIpAddr(request);
        if (!ipAddress.equals(map.get(CLIENT_IP_KEY))) {
            return null;
        }
        String macAddress = MacAddressUtils.getMacAddress(ipAddress);
        if (!macAddress.equals(map.get(CLIENT_MAC_KEY))) {
            return null;
        }
        return (String) map.get(USERNAME_KEY);
    }

    public String verifyGetUserName(String token) {
        Map map = verifyGetMap(token);
        if (null == map) {
            return null;
        }
        return (String) map.get(USERNAME_KEY);
    }

    public Map verifyGetMap(String token) {
        Claims claims = verifyGetClaims(token);
        if (null == claims) {
            return null;
        }
        return claims.get(jwtConfig.getClaimsName(), Map.class);
    }

    public boolean verify(String token) {
        return verifyGetClaims(token) != null;
    }

    public Claims verifyGetClaims(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("Token解析异常：{}", e.getMessage());
            return null;
        }
    }
}

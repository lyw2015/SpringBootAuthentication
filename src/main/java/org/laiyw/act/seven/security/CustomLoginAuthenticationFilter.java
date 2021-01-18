package org.laiyw.act.seven.security;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.laiyw.act.seven.model.ResponseConstants;
import org.laiyw.act.seven.model.ResponseInfo;
import org.laiyw.act.seven.model.UserInfo;
import org.laiyw.act.seven.utils.JwtTokenUtils;
import org.laiyw.act.seven.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/8 10:14
 * @Description TODO
 */
@Slf4j
@Component
public class CustomLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String METHOD_NAME = HttpMethod.POST.name();
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private RedisUtils redisUtils;

    public CustomLoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/login", METHOD_NAME));
        // 认证成功响应
        setAuthenticationSuccessHandler((request, response, authentication) -> {
            log.debug("==================Authentication Successfully--->{}", authentication.getName());
            UserInfo userInfo = (UserInfo) authentication.getPrincipal();
            String token = jwtTokenUtils.createToken(userInfo.getUsername());
            cacheToRedis(token, userInfo);
            userInfo.setToken(token);
            response(response, ResponseInfo.success(authentication.getPrincipal()));
        });
        // 认证失败响应
        setAuthenticationFailureHandler((request, response, e) -> {
            log.debug("==================Authentication Fail--->{}", e.getMessage());
            response(response, ResponseInfo.fail(ResponseConstants.BAD_REQUEST_CODE, e.getMessage()));
        });
    }

    private void cacheToRedis(String token, UserInfo userInfo) {
        String nonPrefix = StringUtils.substring(token, jwtTokenUtils.getJwtConfig().getTokenPrefix().length());
        redisUtils.hashSet(DigestUtils.md5DigestAsHex(nonPrefix.getBytes(Charset.defaultCharset())), userInfo);
    }

    private void response(HttpServletResponse response, ResponseInfo responseInfo) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(JSON.toJSONString(responseInfo));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("==================Authentication======================");
        if (!METHOD_NAME.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        if (!MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
            throw new AuthenticationServiceException("Authentication ContentType not supported: " + request.getContentType());
        }
        LoginParameter loginParameter = null;
        try {
            loginParameter = JSON.parseObject(request.getInputStream(), LoginParameter.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null == loginParameter || StringUtils.isEmpty(loginParameter.getUsername()) || StringUtils.isEmpty(loginParameter.getPassword())) {
            throw new AuthenticationServiceException("用户名密码不能为空");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginParameter.getUsername(), loginParameter.getPassword()
        );
        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    @Data
    static class LoginParameter {
        String username;
        String password;
    }
}

package org.laiyw.act.seven.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.laiyw.act.seven.exception.InvalidTokenException;
import org.laiyw.act.seven.model.UserInfo;
import org.laiyw.act.seven.utils.JwtTokenUtils;
import org.laiyw.act.seven.utils.RedisUtils;
import org.laiyw.act.seven.utils.RequestSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/13 11:42
 * @Description TODO
 */
@Component
public class TokenCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = StringUtils.defaultString(
                request.getHeader(jwtTokenUtils.getJwtConfig().getAuthKey()),
                request.getParameter(jwtTokenUtils.getJwtConfig().getAuthKey())
        );
        if (StringUtils.isEmpty(token) || !StringUtils.startsWith(token, jwtTokenUtils.getJwtConfig().getTokenPrefix())) {
            throw new InvalidTokenException("Invalid Token");
        }
        token = StringUtils.substring(token, jwtTokenUtils.getJwtConfig().getTokenPrefix().length());
        if (StringUtils.isEmpty(token)) {
            throw new InvalidTokenException("Invalid Token");
        }
        // 解析Token
        String key = DigestUtils.md5DigestAsHex(token.getBytes(Charset.defaultCharset()));
        if (!jwtTokenUtils.verify(token)) {
            throw new InvalidTokenException("Invalid Token", key);
        }
        UserInfo userInfo = (UserInfo) redisUtils.hashGet(key);
        if (null == userInfo) {
            throw new InvalidTokenException("Invalid Token", key);
        }
        RequestSecurityUtils.setUser(userInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestSecurityUtils.clean();
    }
}

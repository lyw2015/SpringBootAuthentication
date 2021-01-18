package org.laiyw.act.seven.security;

import org.laiyw.act.seven.model.UserInfo;
import org.laiyw.act.seven.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/7 15:49
 * @Description TODO
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        // 获取用户信息
        UserInfo userInfo = userService.getUserInfoByUserName(username);
        if (null == userInfo || !passwordEncoder.matches((String) authentication.getCredentials(), userInfo.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        checkAccount(userInfo);
        return new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
    }

    private void checkAccount(UserInfo userInfo) {
        if (userInfo.isEnabled()) {
            throw new DisabledException("账号已禁用");
        }
        if (userInfo.isAccountNonLocked()) {
            throw new LockedException("账号已锁定");
        }
        if (userInfo.isAccountNonExpired()) {
            throw new AccountExpiredException("账号已过期");
        }
        if (userInfo.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("密码已过期");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

package org.laiyw.act.seven.config;

import org.laiyw.act.seven.interceptor.TokenCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/13 11:39
 * @Description TODO
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private TokenCheckInterceptor tokenCheckInterceptor;

    private static final List<String> EXCLUDE_PATH_PATTERNS = Arrays.asList(
            "/user/login",
            "/druid/**"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATH_PATTERNS);
    }
}

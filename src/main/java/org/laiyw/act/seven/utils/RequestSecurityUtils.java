package org.laiyw.act.seven.utils;

import org.activiti.engine.impl.identity.Authentication;
import org.laiyw.act.seven.model.UserInfo;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/13 14:32
 * @Description TODO
 */
public class RequestSecurityUtils {

    private static ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();

    public static void setUser(UserInfo user) {
        threadLocal.set(user);
        // 设置流程引擎中的用户
        if (null != user) {
            Authentication.setAuthenticatedUserId(user.getUsername());
        }
    }

    public static UserInfo getUser() {
        return threadLocal.get();
    }

    public static void clean() {
        threadLocal.remove();
    }
}

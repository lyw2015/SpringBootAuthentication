package org.laiyw.act.seven.service;

import org.laiyw.act.seven.model.RoleInfo;

import java.util.List;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/14 10:11
 * @Description TODO
 */
public interface IRoleService {

    /**
     * listByUserName
     *
     * @param userName
     * @return
     */
    List<RoleInfo> listByUserName(String userName);
}

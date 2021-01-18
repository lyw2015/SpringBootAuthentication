package org.laiyw.act.seven.service.impl;

import org.laiyw.act.seven.model.RoleInfo;
import org.laiyw.act.seven.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/14 10:11
 * @Description TODO
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Override
    public List<RoleInfo> listByUserName(String userName) {
        List<RoleInfo> roleInfos = new ArrayList<>();
        roleInfos.add(new RoleInfo(1, "管理员", "admin"));
        roleInfos.add(new RoleInfo(1, "项目经理", "pm"));
        roleInfos.add(new RoleInfo(1, "开发工程师", "dev"));
        return roleInfos;
    }
}

package org.laiyw.act.seven.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.laiyw.act.seven.entity.User;
import org.laiyw.act.seven.model.UserInfo;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/13 16:36
 * @Description TODO
 */
public interface IUserService extends IService<User> {

    /**
     * getUserInfoByUserName
     *
     * @param userName
     * @return
     */
    UserInfo getUserInfoByUserName(String userName);

    /**
     * createUser
     *
     * @param user
     */
    void createUser(User user);

    /**
     * listByPage
     *
     * @param page
     * @return
     */
    IPage listByPage(IPage page);
}

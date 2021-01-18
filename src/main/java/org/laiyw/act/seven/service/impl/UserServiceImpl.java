package org.laiyw.act.seven.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laiyw.act.seven.entity.User;
import org.laiyw.act.seven.mapper.UserMapper;
import org.laiyw.act.seven.model.UserInfo;
import org.laiyw.act.seven.service.IRoleService;
import org.laiyw.act.seven.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/13 16:37
 * @Description TODO
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, UserDetailsService {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return getUserInfoByUserName(s);
    }

    @Override
    public UserInfo getUserInfoByUserName(String userName) {
        User user = userMapper.getByUserName(userName);
        if (null == user) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        userInfo.setRoleInfoList(roleService.listByUserName(userName));
        return userInfo;
    }

    @Override
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.insert();
    }

    @Override
    public IPage listByPage(IPage page) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        IPage selectPage = userMapper.selectPage(page, wrapper);
        log.debug("total:" + selectPage.getTotal());
        return selectPage;
    }
}

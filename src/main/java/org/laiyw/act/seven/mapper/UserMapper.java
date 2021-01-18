package org.laiyw.act.seven.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.laiyw.act.seven.entity.User;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/15 17:46
 * @Description TODO
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * getByUserName
     *
     * @param userName
     * @return
     */
    default User getByUserName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return selectOne(wrapper);
    }

}

package org.laiyw.act.seven.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/15 17:45
 * @Description TODO
 */
@Data
public class User extends Model<User> {

    private Integer id;
    private String userName;
    private String password;
    private String nickName;
}

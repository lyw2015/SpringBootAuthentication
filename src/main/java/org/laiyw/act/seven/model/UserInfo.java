package org.laiyw.act.seven.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/7 16:15
 * @Description TODO
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo implements UserDetails, Serializable {

    private Integer id;
    private String nickName;
    private String userName;
    @JSONField(serialize = false)
    @JsonIgnore
    private String password;
    /**
     * 禁用
     */
    private int enable;
    /**
     * 锁定
     */
    private int locked;
    /**
     * 账号过期时间
     */
    private Date accountExpired;
    /**
     * 密码过期时间
     */
    private Date credentialsExpired;

    private String token;

    private List<RoleInfo> roleInfoList;

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roleInfoList;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public String getUsername() {
        return this.userName;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        if (null == this.accountExpired) {
            return false;
        }
        return this.accountExpired.before(new Date());
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.locked == 1;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        if (null == this.credentialsExpired) {
            return false;
        }
        return this.credentialsExpired.before(new Date());
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.enable == 1;
    }

}

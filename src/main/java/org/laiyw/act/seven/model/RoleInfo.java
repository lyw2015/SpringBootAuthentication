package org.laiyw.act.seven.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/8 9:07
 * @Description TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleInfo implements GrantedAuthority {

    private Integer roleId;
    private String roleName;
    private String roleCode;

    @Override
    public String getAuthority() {
        return this.roleCode;
    }

}

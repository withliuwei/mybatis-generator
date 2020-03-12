package vip.liuw.myproject.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 用户角色关联表
 */
@Getter
@Setter
public class SysUserRole {
    @Id
    @Column(name = "`id`")
    private String id;

    @Column(name = "`user_id`")
    private String userId;

    @Column(name = "`role_id`")
    private String roleId;
}
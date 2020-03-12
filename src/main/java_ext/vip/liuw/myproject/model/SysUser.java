package vip.liuw.myproject.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * 用户表
 */
@Getter
@Setter
public class SysUser {
    //============= Transient Field ==================
    @Transient
    private List<SysRole> sysRoles;

    @Transient
    private SysDepartment sysDepartment;

    //============= Table Field ==================

    @Id
    @Column(name = "`id`")
    private String id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`department_id`")
    private String departmentId;
}
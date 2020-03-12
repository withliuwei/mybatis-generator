package vip.liuw.myproject.model;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 角色表
 */
public class SysRole {
    @Id
    @Column(name = "`id`")
    private Integer id;

    @Column(name = "`name`")
    private String name;

}
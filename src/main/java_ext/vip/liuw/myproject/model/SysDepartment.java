package vip.liuw.myproject.model;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 部门表
 */
public class SysDepartment {
    @Id
    @Column(name = "`id`")
    private Integer id;

    @Column(name = "`name`")
    private String name;

}
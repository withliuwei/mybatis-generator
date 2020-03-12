package vip.liuw.myproject.model;

import javax.persistence.Column;
import java.util.Date;

public class BaseEntity {

    @Column(name = "`create_date`")
    private Date createDate;
}

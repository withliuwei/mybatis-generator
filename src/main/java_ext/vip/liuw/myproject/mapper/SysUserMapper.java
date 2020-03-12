package vip.liuw.myproject.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import vip.liuw.myproject.model.SysUser;

public interface SysUserMapper extends Mapper<SysUser> {
    SysUser getSysUserDetail(@Param("id") String id);
}
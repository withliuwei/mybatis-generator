package vip.liuw.myproject.mapper;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * 父类Mapper，实现多个接口，其它Mapper只需继承即可
 */
public interface BaseMapper<T> extends Mapper<T>, IdsMapper<T> {
}

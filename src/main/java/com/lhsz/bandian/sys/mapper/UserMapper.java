package com.lhsz.bandian.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.sys.DTO.result.UserDTO;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
public interface UserMapper extends MyBaseMapper<User> {


    List<UserDTO> selectPageDTO(@Param("ew") Wrapper ew);

    List<User> selectListDTo(QueryWrapper queryWrapper);
}

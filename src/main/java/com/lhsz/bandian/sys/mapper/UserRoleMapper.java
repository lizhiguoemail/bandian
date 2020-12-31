package com.lhsz.bandian.sys.mapper;

import com.lhsz.bandian.sys.entity.UserRole;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Delete;

/**
 * <p>
 * 用户角色 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
public interface UserRoleMapper extends MyBaseMapper<UserRole> {

    @Delete(" delete from sys_user_role where user_id = #{uid}")
    void trueToDelete(String uid);
}

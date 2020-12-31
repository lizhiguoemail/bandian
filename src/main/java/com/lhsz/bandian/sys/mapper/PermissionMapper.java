package com.lhsz.bandian.sys.mapper;

import com.lhsz.bandian.sys.entity.Permission;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Delete;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
public interface PermissionMapper extends MyBaseMapper<Permission> {

    @Delete("delete from sys_permission where role_id = #{roleId}")
    void trueTODelete(String roleId);
}

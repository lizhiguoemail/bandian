package com.lhsz.bandian.sys.service;

import com.lhsz.bandian.sys.DTO.query.QueryRoleDTO;
import com.lhsz.bandian.sys.DTO.result.RoleDTO;
import com.lhsz.bandian.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
public interface IRoleService extends IService<Role> {

    List<RoleDTO> listQuery(QueryRoleDTO queryRoleDTO);
}

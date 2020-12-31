package com.lhsz.bandian.sys.service;

import com.lhsz.bandian.sys.DTO.query.QueryRoleDTO;
import com.lhsz.bandian.sys.DTO.result.RoleDTO;
import com.lhsz.bandian.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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

    List<Map<String, String>> listAllRole();
    boolean removeAllByRoleId(String id);

    boolean addRole(RoleDTO roleDTO);

    boolean updateRole(RoleDTO roleDTO);
}

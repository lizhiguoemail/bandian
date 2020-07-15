package com.lhsz.bandian.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.sys.DTO.query.QueryRoleDTO;
import com.lhsz.bandian.sys.DTO.result.RoleDTO;
import com.lhsz.bandian.sys.entity.Role;
import com.lhsz.bandian.sys.mapper.RoleMapper;
import com.lhsz.bandian.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<RoleDTO> listQuery(QueryRoleDTO queryRoleDTO) {
        List<RoleDTO> list=new ArrayList<>();
        QueryWrapper queryWrapper =new QueryWrapper();
        if(queryRoleDTO.getCode()!=null&&!"".equals(queryRoleDTO.getCode().trim())){
            queryWrapper.like("code",queryRoleDTO.getCode());
        }
        if(queryRoleDTO.getName()!=null&&!"".equals(queryRoleDTO.getName().trim())){
            queryWrapper.like("name",queryRoleDTO.getName());
        }
        if(queryRoleDTO.getType()!=null&&!"".equals(queryRoleDTO.getType().trim())){
            queryWrapper.like("type",queryRoleDTO.getName());
        }
        if(queryRoleDTO.getIsAdmin()!=null&&!"".equals(queryRoleDTO.getIsAdmin().trim())){
            queryWrapper.eq("is_admin",!queryRoleDTO.getIsAdmin().equals("true"));
        }
        if(queryRoleDTO.getEnabled()!=null&&!"".equals(queryRoleDTO.getEnabled().trim())){
            queryWrapper.eq("enabled",queryRoleDTO.getEnabled().equals("true"));
        }
        List<Role> roleList=roleMapper.selectList(queryWrapper);
        for (Role role : roleList) {
            RoleDTO roleDTO=new RoleDTO();
            BeanUtils.copyProperties(role,roleDTO);
            roleDTO.setId(role.getRoleId());
            list.add(roleDTO);
        }
        return list;
    }
}

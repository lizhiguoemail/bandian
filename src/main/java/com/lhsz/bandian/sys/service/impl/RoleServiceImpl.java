package com.lhsz.bandian.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.sys.DTO.query.QueryRoleDTO;
import com.lhsz.bandian.sys.DTO.result.RoleDTO;
import com.lhsz.bandian.sys.entity.Role;
import com.lhsz.bandian.sys.mapper.RoleMapper;
import com.lhsz.bandian.sys.service.IPermissionService;
import com.lhsz.bandian.sys.service.IRoleService;
import com.lhsz.bandian.sys.service.IUserRoleService;
import com.lhsz.bandian.utils.CacheableString;
import com.lhsz.bandian.utils.Convert;
import com.lhsz.bandian.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    IUserRoleService userRoleService;
    @Autowired
    IPermissionService permissionService;
    @Autowired
    RedisCache redisCache;
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
            queryWrapper.eq("is_admin",!"true".equals(queryRoleDTO.getIsAdmin()));
        }
        if(queryRoleDTO.getEnabled()!=null&&!"".equals(queryRoleDTO.getEnabled().trim())){
            queryWrapper.eq("enabled", "true".equals(queryRoleDTO.getEnabled()));
        }
        List<Role> roleList=baseMapper.selectList(queryWrapper);
        for (Role role : roleList) {
            RoleDTO roleDTO=new RoleDTO();
            BeanUtils.copyProperties(role,roleDTO);
            roleDTO.setId(role.getRoleId());
            list.add(roleDTO);
        }
        return list;
    }


    @Override
    public List<Map<String, String>> listAllRole() {
        List<Role> listRole=super.list();
        List<Map<String,String>> list=new ArrayList<>();
        for (Role role : listRole) {
            Map<String,String> map=new HashMap<>();
            map.put("text",role.getName());
            map.put("value",role.getRoleId());
            list.add(map);
        }
        redisCache.setCacheList(CacheableString.ROLELIST,list);
        return list;
    }
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean removeAllByRoleId(String id) {
        boolean b1=removeById(id);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("role_id",id);
        userRoleService.remove(queryWrapper);
        permissionService.remove(queryWrapper);
        redisCache.deleteObject(CacheableString.ROLELIST);
        if(b1){
            return true;
        }
        return false;
    }

    @Override
    public boolean addRole(RoleDTO roleDTO) {
        Role role =new Role();
        BeanUtils.copyProperties(roleDTO,role);
        role.setNormalizedName(Convert.toUpperCase(role.getName()));
        role.setType("Role");
        role.setIsAdmin(false);
        redisCache.deleteObject(CacheableString.ROLELIST);
        return save(role);
    }

    @Override
    public boolean updateRole(RoleDTO roleDTO) {
        Role role =new Role();
        BeanUtils.copyProperties(roleDTO,role);
        role.setRoleId(roleDTO.getId());
        role.setNormalizedName(Convert.toUpperCase(role.getName()));
        role.setType("Role");
        role.setIsAdmin(false);
        redisCache.deleteObject(CacheableString.ROLELIST);
        return updateById(role);
    }
}

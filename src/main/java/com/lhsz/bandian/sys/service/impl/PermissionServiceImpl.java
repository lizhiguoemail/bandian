package com.lhsz.bandian.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.sys.entity.Permission;
import com.lhsz.bandian.sys.mapper.PermissionMapper;
import com.lhsz.bandian.sys.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {


    @Autowired
    PermissionMapper permissionMapper;
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void savePermission(String roleId, String resourceIds) {
        String[] rs = resourceIds.split(",");
        List<Permission> addlist=new ArrayList<>();
        for (int i = 0; i < rs.length; i++) {
            Permission per=new Permission();
            per.setRoleId(roleId);
            per.setResourceId(rs[i]);
            addlist.add(per);
        }
        permissionMapper.trueTODelete(roleId);
        if(addlist.size()>0){
            this.saveBatch(addlist);
        }

    }


}

package com.lhsz.bandian.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.sys.DTO.commit.AddPermissionDTO;
import com.lhsz.bandian.sys.entity.Permission;
import com.lhsz.bandian.sys.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/systems/permission")
public class PermissionController extends BaseController {

    @Autowired
    IPermissionService  permissionService;
    @GetMapping("/resourceIds")
    @PreAuthorize("hasAuthority('systems:permission:resourceIds')")
    public HttpResult all(String roleId){
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("role_id",roleId);
        List<Permission> list = permissionService.list(queryWrapper);
        Set<String> resourceIds=new HashSet<>();
        list.forEach(per->{
            resourceIds.add(per.getResourceId());
        });
        return HttpResult.ok(resourceIds);
    }
    @PostMapping("/save")
    public HttpResult save(@RequestBody AddPermissionDTO permission){
        String roleId=permission.getRoleId();
        String resourceIds=permission.getResourceIds();
        permissionService.savePermission(roleId,resourceIds);
        return HttpResult.ok();
    }



}

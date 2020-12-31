package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.sys.DTO.query.QueryRoleDTO;
import com.lhsz.bandian.sys.DTO.result.RoleDTO;
import com.lhsz.bandian.sys.entity.Role;
import com.lhsz.bandian.sys.service.IRoleService;
import com.lhsz.bandian.utils.CacheableString;
import com.lhsz.bandian.utils.Convert;
import com.lhsz.bandian.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/systems/role")
public class RoleController extends BaseController {
    @Autowired
    IRoleService roleService;
    @GetMapping("/all")
    public HttpResult all(){

        List<Map<String,String>> list= roleService.listAllRole();
        return HttpResult.ok(list);
    }
    @GetMapping()
    @PreAuthorize("hasAuthority('systems:role:list')")
    public HttpResult list(QueryRoleDTO queryRoleDTO){
        startPage();
        return HttpResult.ok(roleService.listQuery(queryRoleDTO));
//        return HttpResult.ok(list);
    }
    @GetMapping("/{id}")
    public HttpResult list(@PathVariable("id") String id){
       Role role = roleService.getById(id);
       RoleDTO roleDTO = new RoleDTO();
       BeanUtils.copyProperties(role,roleDTO);
       roleDTO.setId(role.getRoleId());
        return HttpResult.ok(roleDTO);
    }
    @PostMapping()
    @PreAuthorize("hasAuthority('systems:role:add')")
    public HttpResult add(@RequestBody RoleDTO roleDTO){

        if(roleService.addRole(roleDTO)){
            return HttpResult.succee();
        }else{
            return HttpResult.fail();
        }
    }
    @PutMapping()
    @PreAuthorize("hasAuthority('systems:role:update')")
    public HttpResult update(@RequestBody RoleDTO roleDTO){
        Role role =new Role();
        BeanUtils.copyProperties(roleDTO,role);
        role.setRoleId(roleDTO.getId());
        role.setNormalizedName(Convert.toUpperCase(role.getName()));
        role.setType("Role");
        role.setIsAdmin(false);

        if(roleService.updateRole(roleDTO)){
            return HttpResult.succee();
        }
        return HttpResult.fail();
    }
    /**
     * 删除角色
     * ？该角色下的用户关系
     * ？角色与权限的关系
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('systems:role:delete')")
    public HttpResult delete(@PathVariable("id") String id){

        if(!roleService.removeAllByRoleId(id)){
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }

}

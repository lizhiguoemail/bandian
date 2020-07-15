package com.lhsz.bandian.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.pojo.HttpResult;
import com.lhsz.bandian.sys.DTO.query.QueryRoleDTO;
import com.lhsz.bandian.sys.DTO.result.RoleDTO;
import com.lhsz.bandian.sys.entity.Role;
import com.lhsz.bandian.sys.service.IRoleService;
import com.lhsz.bandian.utils.Convert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
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
   /* @GetMapping("/all")
    public HttpResult all(){
        List<Role> listRole=roleService.list();
        List<RoleDTO> list=new ArrayList<>();
        for (Role role : listRole) {
            RoleDTO roleDTO=new RoleDTO();
            BeanUtils.copyProperties(role,roleDTO);
            roleDTO.setId(role.getRoleId());
            list.add(roleDTO);
        }
        return HttpResult.ok(list);
    } */
    @GetMapping("/all")
    public HttpResult all(){
        List<Role> listRole=roleService.list();
        List<Map<String,String>> list=new ArrayList<>();
        for (Role role : listRole) {
            Map<String,String> map=new HashMap<>();
            map.put("text",role.getName());
            map.put("value",role.getRoleId());
            list.add(map);
        }
        return HttpResult.ok(list);
    }
    @GetMapping("")
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
    @PostMapping("")
    public HttpResult add(@RequestBody RoleDTO roleDTO){
        Role role =new Role();
        BeanUtils.copyProperties(roleDTO,role);
        role.setNormalizedName(Convert.toUpperCase(role.getName()));
        role.setType("Role");
        role.setIsAdmin(false);
        if(roleService.save(role)){
            return HttpResult.succee();
        }else{
            return HttpResult.fail();
        }
    }


}

package com.lhsz.bandian.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.sys.DTO.query.QueryDeptDTO;
import com.lhsz.bandian.sys.DTO.result.DeptDTO;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;
import com.lhsz.bandian.sys.entity.Dept;
import com.lhsz.bandian.sys.service.IDeptService;
import com.lhsz.bandian.utils.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/systems/department")
public class DeptController extends BaseController {
    @Autowired
    IDeptService deptService;
    @GetMapping()
    @PreAuthorize("hasAuthority('systems:department:list')")
    public HttpResult list(QueryDeptDTO queryDeptDTO){
        List<DeptDTO> listDTO=deptService.listDTO(queryDeptDTO);
        return HttpResult.ok(listDTO);
    }
    @GetMapping("/tree")
    public HttpResult tree(){
        TreeDTO treeDTO=deptService.getTreeDTO();
        return HttpResult.ok(treeDTO);
    }
    @PostMapping()
    @PreAuthorize("hasAuthority('systems:department:add')")
    public HttpResult add(@RequestBody Dept dept){
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",dept.getName());
        queryWrapper.eq("parent_id",dept.getParentId());
        List<Dept> list = deptService.list(queryWrapper);
        if(list.size()>0){
            return HttpResult.fail("部门名称已存在！");
        }
        String id= UUID.randomUUID().toString();
        Dept parentDept = deptService.getById(dept.getParentId());
        if(parentDept!=null){
            dept.setPath(parentDept.getPath()+id+",");
            dept.setDeptId(id);
            dept.setLevel(parentDept.getLevel()+1);
        }
        if(deptService.save(dept)){
            return HttpResult.ok();
        }
        return HttpResult.fail();
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('systems:department:delete')")
    public HttpResult deleteById(@PathVariable("id") String id){
        if( deptService.removeById(id)){
            return HttpResult.ok();
        }
        return HttpResult.fail();
    }
    /**
     * 详情
     * @param id ID
     * @return Dept
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('systems:department:show')")
    public HttpResult showUser(@PathVariable("id") String id){
        Dept dept = deptService.getById(id);
        DeptDTO deptDTO= new DeptDTO(dept);
        return HttpResult.ok(deptDTO);
    }
    @PutMapping()
    @PreAuthorize("hasAuthority('systems:department:update')")
    public HttpResult update(@RequestBody DeptDTO dept){
        Dept parentDept = deptService.getById(dept.getParentId());
        Dept oldDept = deptService.getById(dept.getDeptId());
        if(!oldDept.getName().equals(dept.getName())){
            QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("name",dept.getName());
            queryWrapper.eq("parent_id",dept.getParentId());
            List<Dept> list = deptService.list(queryWrapper);
            if(list.size()>0){
                return HttpResult.fail("部门名称已被使用！");
            }
        }
        if(parentDept!=null){
            dept.setPath(parentDept.getPath()+dept.getDeptId()+",");
            dept.setLevel(parentDept.getLevel()+1);
        }
        CopyUtils.copyProperties(dept,oldDept);
        if(deptService.saveOrUpdate(oldDept)){
            return HttpResult.ok();
        }
        return HttpResult.fail();
    }
//    systems:department:delete:deleteAll

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('systems:department:deleteAll')")
    public HttpResult delete(@RequestBody() String ids){
        ids= ids.replace("\"", "");
        String[] split = ids.split(",");
        if(deptService.removeByIds(Arrays.asList(split))){
            return HttpResult.succee();
        }
        return HttpResult.fail();
    }
}

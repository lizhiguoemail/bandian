package com.lhsz.bandian.sys.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.sys.DTO.commit.AddResourceDTO;
import com.lhsz.bandian.sys.DTO.query.QueryMoudleDTO;
import com.lhsz.bandian.sys.DTO.result.MoudelDTO;
import com.lhsz.bandian.sys.DTO.result.MoudelParentDTO;
import com.lhsz.bandian.sys.entity.Resource;
import com.lhsz.bandian.sys.service.IMoudleService;
import com.lhsz.bandian.sys.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizhiguo
 * 2020/7/20 10:36
 */
@RestController
@RequestMapping("/systems/module")
public class ModuleController extends BaseController {
    @Autowired
    IMoudleService moudleService;
    @Autowired
    IResourceService resourceService;

    /**
     * 根据AppID获取资源列表
     * @param applicationId 应用程序ID
     * @return 模块集合
     */

    @GetMapping("/permission")
    public HttpResult all(String applicationId,String roleId){
        List<MoudelDTO> list = moudleService.listByAppId(applicationId,roleId);
        return HttpResult.ok(list);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('systems:module:list')")
    public HttpResult list(QueryMoudleDTO queryMoudleDTO){
        List<MoudelDTO> list=moudleService.listByAppId(queryMoudleDTO);
        return HttpResult.ok(list);
    }
    @GetMapping("/tree")
    public HttpResult tree(String ApplicationId){
        List<MoudelParentDTO> list=moudleService.listMouleForAdd(ApplicationId);
        Map<String,Object> map =new HashMap<>();
        map.put("nodes",list);
        logb.debug(JSON.toJSONString(map));
        return HttpResult.ok(map);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('systems:module:add')")
    public HttpResult add(@RequestBody AddResourceDTO resourceDTO){
        if(resourceDTO.getParentId()==null||"".equals(resourceDTO.getParentId().trim())){
            return HttpResult.fail("父模块不能为空！");
        }
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",resourceDTO.getName());
        queryWrapper.eq("parent_id",resourceDTO.getParentId());
        List<Resource> list = resourceService.list(queryWrapper);
        if(list.size()>0){
            return HttpResult.fail("模块名已存在！");
        }
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uri",resourceDTO.getUrl());
        List<Resource> list2 = resourceService.list(queryWrapper);
        if(list2.size()>0){
            return HttpResult.fail("地址已存在！");
        }
        if(moudleService.addMoudle(resourceDTO)){
            return HttpResult.succee();
        }else {
            return HttpResult.fail();
        }
    }
    /**
     * 根据ID删除用户
     * @param id 用户ID
     * @return HttpResult
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('systems:module:delete')")
    public HttpResult deleteUser(@PathVariable("id") String id){
        if(!resourceService.del(id)){
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('systems:module:show')")
    public HttpResult findById(@PathVariable("id") String id){
        MoudelDTO moudelDTO=moudleService.findByRid(id);
         return HttpResult.ok(moudelDTO);
    }
    @PutMapping()
    @PreAuthorize("hasAuthority('systems:module:update')")
    public HttpResult update(@RequestBody MoudelDTO moudelDTO){
        Resource oldResource = resourceService.getById(moudelDTO.getId());
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        if(!oldResource.getName().equals(moudelDTO.getName())){
            queryWrapper.eq("name",moudelDTO.getName());
            queryWrapper.eq("parent_id",moudelDTO.getParentId());
            List<Resource> list = resourceService.list(queryWrapper);
            if(list.size()>0){
                return HttpResult.fail("模块名已存在！");
            }
        }
        if(!oldResource.getUri().equals(moudelDTO.getUrl())){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uri",moudelDTO.getUrl());
            List<Resource> list2 = resourceService.list(queryWrapper);
            if(list2.size()>0){
                return HttpResult.fail("地址已存在！");
            }
        }
        if(moudleService.updateMoudle(moudelDTO)){
            return HttpResult.succee();
        }else {
            return HttpResult.fail();
        }
    }
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('systems:module:deleteAll')")
    public HttpResult delete(@RequestBody() String ids){
        ids= ids.replace("\"", "");
        String[] split = ids.split(",");
        if(moudleService.removeByIds(Arrays.asList(split))){
            return HttpResult.succee();
        }
        return HttpResult.fail();
    }
}

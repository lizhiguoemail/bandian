package com.lhsz.bandian.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.sys.DTO.Extend;
import com.lhsz.bandian.sys.DTO.commit.AddResourceDTO;
import com.lhsz.bandian.sys.DTO.query.QueryMoudleDTO;
import com.lhsz.bandian.sys.DTO.result.MoudelDTO;
import com.lhsz.bandian.sys.DTO.result.MoudelParentDTO;
import com.lhsz.bandian.sys.entity.Permission;
import com.lhsz.bandian.sys.entity.Resource;
import com.lhsz.bandian.sys.service.IApplicationService;
import com.lhsz.bandian.sys.service.IMoudleService;
import com.lhsz.bandian.sys.service.IPermissionService;
import com.lhsz.bandian.sys.service.IResourceService;
import com.lhsz.bandian.utils.UtileTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lizhiguo
 * 2020/7/20 11:48
 */
@Service
public class MoudelServcieImpl implements IMoudleService {
    @Autowired
    IApplicationService applicationService;
    @Autowired
    IResourceService resourceService;
    @Autowired
    IPermissionService permissionService;
    @Override
    public List<MoudelDTO> listByAppId(String applicationId,String roleId) {
        QueryWrapper<Resource> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("application_id",applicationId);
        queryWrapper.orderByAsc("path");
        List<Resource> resourceList = resourceService.list(queryWrapper);
        return buildMoudeTree(convertDTO(resourceList,roleId));
    }
    @Override
    public List<MoudelDTO> listByAppId(String applicationId) {
        QueryWrapper<Resource> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("application_id",applicationId);
        queryWrapper.orderByAsc("path");
        List<Resource> resourceList = resourceService.list(queryWrapper);
        return convertDTO(resourceList,null);
    }
    @Override
    public List<MoudelDTO> listByAppId(QueryMoudleDTO queryMoudleDTO) {
        QueryWrapper<Resource> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("application_id",queryMoudleDTO.getApplicationId());
        if(queryMoudleDTO.getUri()!=null) {
            queryWrapper.like("uri",queryMoudleDTO.getUri());
        }
        if(queryMoudleDTO.getName()!=null) {
            queryWrapper.like("name",queryMoudleDTO.getName());
        }
        queryWrapper.eq("enabled",queryMoudleDTO.isEnabled());
         List<Resource> list1 = resourceService.list(queryWrapper);
        List<Resource> resourceList = addParentResource(list1);
        return convertDTO(resourceList,null);
    }
    @Override
    public List<MoudelParentDTO> listMouleForAdd(String applicationId) {
        QueryWrapper<Resource> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("application_id",applicationId);
        queryWrapper.orderByAsc("path");
        List<Resource> resourceList = resourceService.list(queryWrapper);
        return buildMenuTree(convertMoudelParentDTO(resourceList),null);
    }

    @Override
    public MoudelDTO findByRid(String id) {
       Resource resource = resourceService.getById(id);
       MoudelDTO moudelDTO=new MoudelDTO();
       Extend extend =new Extend(resource.getExtend());
        BeanUtils.copyProperties(resource,moudelDTO);
        moudelDTO.setUrl(resource.getUri());
        moudelDTO.setIcon(extend.getIcon());
        moudelDTO.setMethod(extend.getMethod());
        moudelDTO.setExpanded(extend.isExpanded());
        moudelDTO.setId(resource.getResourceId());
        return moudelDTO;
    }

    @Override
    public boolean addMoudle(AddResourceDTO resourceDTO) {
        Resource resourceParent = resourceService.getById(resourceDTO.getParentId());
        Resource resource =new Resource();
        resource.setResourceId(UtileTools.randomUUID());
        resource.setApplicationId(resourceDTO.getApplicationId());
        resource.setEnabled(resourceDTO.isEnabled());
        resource.setName(resourceDTO.getName());
        resource.setUri(resourceDTO.getUrl());
        resource.setParentId(resourceDTO.getParentId());
        resource.setExtend(new Extend(resourceDTO.getIcon(),false,resourceDTO.getMethod()).toJson());
        resource.setPath(resourceParent.getPath()+resource.getResourceId()+",");
        resource.setLevel(resourceParent.getLevel()+1);
        resource.setSortId(resourceDTO.getSortId());
        resource.setRemark(resourceDTO.getRemark());
        resource.setType(resourceDTO.getType());

        //新增模块时，自动添加基础功能按钮
        if(resourceDTO.getType()==1){
            if(resource.getUri()!=null) {
                addForResoure(resourceDTO, resource);
            }
        }
        return resourceService.save(resource);
    }

    private void addForResoure(AddResourceDTO resourceDTO, Resource resource) {
        List<Resource> batchList=new ArrayList<>();
        Map<String,String> uriMap=new HashMap<>();
        uriMap.put("list","查询");
        uriMap.put("add","新增");
        uriMap.put("update","修改");
        uriMap.put("delete","删除");
        uriMap.put("show","详情");
        uriMap.put("deleteAll","批量删除");
        Map<String,String> methdMap=new HashMap<>();
        methdMap.put("list","Get");
        methdMap.put("add","Post");
        methdMap.put("update","Put");
        methdMap.put("delete","Delete");
        methdMap.put("show","Get");
        methdMap.put("deleteAll","Post");
        if(resource.getUri()!=null){
            String[] uri = resource.getUri().split("/");
            StringBuilder url=new StringBuilder();
            for (int i = 1; i < uri.length; i++) {
                url.append(uri[i]);
                url.append(":");
            }
            int sort=0;
            for(Map.Entry<String, String> entry : uriMap.entrySet()){
                sort++;
                String mapKey = entry.getKey();
                String mapValue = entry.getValue();
                Resource resource2 =new Resource();
                resource2.setResourceId(UtileTools.randomUUID());
                resource2.setApplicationId(resourceDTO.getApplicationId());
                resource2.setEnabled(resourceDTO.isEnabled());
                resource2.setName(mapValue);
                resource2.setUri(url.toString()+mapKey);
                resource2.setParentId(resource.getResourceId());
                resource2.setExtend(new Extend(resourceDTO.getIcon(),false,methdMap.get(mapKey)).toJson());
                resource2.setPath(resource.getPath()+resource2.getResourceId()+",");
                resource2.setLevel(resource.getLevel()+1);
                resource2.setSortId(sort);
                resource2.setRemark(resourceDTO.getRemark());
                resource2.setType(2);
                batchList.add(resource2);
            }
        }
        if(batchList.size()>0){
            resourceService.saveBatch(batchList);
        }
    }

    @Override
    public boolean updateMoudle(MoudelDTO moudelDTO) {
        Resource resourceParent = resourceService.getById(moudelDTO.getParentId());
        Resource resource =new Resource();
        BeanUtils.copyProperties(moudelDTO,resource);
        resource.setResourceId(moudelDTO.getId());
        resource.setUri(moudelDTO.getUrl());
        resource.setExtend(new Extend(moudelDTO.getIcon(),false,moudelDTO.getMethod()).toJson());
        resource.setPath(resourceParent.getPath()+resource.getResourceId()+",");
        resource.setLevel(resourceParent.getLevel()+1);
        return resourceService.saveOrUpdate(resource);
    }

    @Override
    public boolean removeByIds(List<String> asList) {
        return resourceService.removeByIds(asList);
    }


    private List<MoudelParentDTO> convertMoudelParentDTO(List<Resource> resourceList) {
        List<MoudelParentDTO> moudelParentDTOList = new ArrayList<>();
        if(resourceList.size()>0) {
            for (Resource resource : resourceList) {
                MoudelParentDTO moudelParentDTO=new MoudelParentDTO();
                Extend extend=new Extend(resource.getExtend());
                moudelParentDTO.setChecked(false);
                moudelParentDTO.setExpanded(extend.isExpanded());
                moudelParentDTO.setId(resource.getResourceId());
                moudelParentDTO.setKey(resource.getResourceId());
                moudelParentDTO.setLevel(resource.getLevel());
                moudelParentDTO.setParentId(resource.getParentId());
                moudelParentDTO.setTitle(resource.getName());
                moudelParentDTO.setLeaf(checkLeaf(resourceList,resource.getResourceId()));
                moudelParentDTOList.add(moudelParentDTO);
            }
        }
        return moudelParentDTOList;
    }
    private boolean checkLeaf(List<Resource> resourceList,String id){
        boolean b=true;
        for (Resource resource : resourceList) {
            if(id.equals(resource.getParentId())){
                b=false;
                break;
            }
        }
        return b;
    }

    private List<MoudelParentDTO> buildMenuTree(List<MoudelParentDTO> menuList, String pid) {
        List<MoudelParentDTO> treeList = new ArrayList<>();
        menuList.forEach(menu -> {
            if (StringUtils.equals(pid, menu.getParentId())) {
                menu.setChildren(buildMenuTree(menuList, menu.getId()));
                treeList.add(menu);
            }
        });
        return treeList;
    }

    private List<MoudelDTO> convertDTO(List<Resource> resourceList,String roleId){
        List<MoudelDTO> moudelDTOList = new ArrayList<>();
        Set<String> permissionps=null;
        if(roleId!=null){
           permissionps=getPermission(roleId);
        }

        for (Resource resource : resourceList) {
            MoudelDTO moudelDTO=new MoudelDTO();
            Extend extend=new Extend(resource.getExtend());
//            moudelDTO.setApplicationId(moudelDTO2.getApplicationId());
            moudelDTO.setId(resource.getResourceId());
            if(permissionps!=null){
                moudelDTO.setChecked(checkPermission(moudelDTO.getId(),permissionps));//判断
            }
            moudelDTO.setCreationTime(resource.getCreationTime());
            moudelDTO.setCreatorId(resource.getCreatorId());
            moudelDTO.setEnabled(true);
            moudelDTO.setExpanded(extend.isExpanded());
            moudelDTO.setIcon(extend.getIcon());
            moudelDTO.setLeaf(checkLeaf(resourceList,resource.getResourceId()));//type==2 表示是按钮 =1是菜单
            moudelDTO.setLevel(resource.getLevel());
            moudelDTO.setMethod(extend.getMethod());
            moudelDTO.setName(resource.getName());
            moudelDTO.setTitle(resource.getName());
            moudelDTO.setParentId(resource.getParentId());
            moudelDTO.setPath(resource.getPath());
            moudelDTO.setRemark(resource.getRemark());
            moudelDTO.setSelectable(true);
            moudelDTO.setSelected(null);
            moudelDTO.setSortId(resource.getSortId());
            moudelDTO.setText(null);
            moudelDTO.setType(resource.getType());
            moudelDTO.setUrl(resource.getUri());
            moudelDTO.setVersion(resource.getVersion());

            moudelDTOList.add(moudelDTO);
        }

        return moudelDTOList;
    }

    private boolean checkPermission(String rId,Set<String> permissionps) {
       boolean b=false;
        //判断是否存在
        for(String v:permissionps){
            if(v.equals(rId)){
                b=true; break;
            }
        }
        return b;
    }

    private Set<String> getPermission(String roleId){
        QueryWrapper<Permission> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<Permission> list = permissionService.list(queryWrapper);
        Set<String> resourceIds=new HashSet<>();
        list.forEach(per-> resourceIds.add(per.getResourceId()));
        return resourceIds;
    }
    private List<MoudelDTO> buildMoudeTree(List<MoudelDTO> menuList) {
        List<MoudelDTO> treeList = new ArrayList<>();
        List<MoudelDTO> treeList2 = new ArrayList<>();
        menuList.forEach(menu -> {
            menu.setParentName(getParentName(menuList,menu.getParentId()));
           if(menu.getType()==1){
               treeList.add(menu);
           }
           else{
               treeList2.add(menu);
           }
        });
        for (MoudelDTO moudelDTO : treeList) {
            moudelDTO.setOperators(level4(moudelDTO.getId(),treeList2));
        }
        return treeList;
    }

    private List<MoudelDTO> level4(String id,List<MoudelDTO> treeList2) {
        List<MoudelDTO> moude4List = new ArrayList<>();
        treeList2.forEach(obj->{
            if(obj.getParentId().equals(id)){
                moude4List.add(obj);
            }
        });
        return moude4List;
    }

    private String getParentName( List<MoudelDTO> list,String id){
        String name=null;
        for (MoudelDTO moudelDTO : list) {
            if(moudelDTO.getId().equals(id)){
                name= moudelDTO.getName();
                break;
            }
        }
        return name;
    }
    private List<Resource> addParentResource(List<Resource> listResource) {
        List<Resource> result=new ArrayList<>();
        //获取path, 根据PATH获取父级菜单ID，再从数据库查询全部数据
        StringBuffer ids=new StringBuffer();
        listResource.forEach(obj->{
            String path = obj.getPath();
            String rid=obj.getResourceId();
            String[] split = path.split(",");
            ids.append("'").append(rid).append("',");
            for (String s : split) {
                if(!rid.equals(s)){
                    ids.append("'").append(s).append("',");
                }
            }
        });
        if(ids.length()>0){
            ids.deleteCharAt(ids.length()-1);
            QueryWrapper<Resource> queryWrapper =new QueryWrapper<>();
            queryWrapper.inSql("resource_id",ids.toString());
            queryWrapper.orderByAsc("path");
            result=resourceService.list(queryWrapper);
        }
        return result;
    }
}

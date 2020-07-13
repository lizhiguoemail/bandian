package com.lhsz.bandian.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.DTO.AppData;
import com.lhsz.bandian.sys.DTO.ResourceDTO;
import com.lhsz.bandian.sys.entity.Application;
import com.lhsz.bandian.sys.entity.Resource;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.mapper.UserMapper;
import com.lhsz.bandian.sys.service.IApplicationService;
import com.lhsz.bandian.sys.service.IResourceService;
import com.lhsz.bandian.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    private IApplicationService applicationService;
    @Autowired
    private IResourceService resourceService;
    @Autowired
    private TokenService tokenService;
    @Override
    public User findByUsername(String username) {
        User user=null;
        try {
            user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name",username));
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String> permissions = new HashSet<>();
        permissions.add("sys:user:view");
        permissions.add("sys:user:add");
        permissions.add("sys:user:edit");
        permissions.add("sys:user:delete");
        return permissions;
    }
    public int del(String id){
        return userMapper.deleteById(id);
    }

    @Override
    public AppData getApp_data() {
        AppData appData=new AppData();
        User user=tokenService.getLoginUserToUser();//获取登录用户
        Application app=tokenService.getLoginUserToApp();//获取登录系统信息
        /*SELECT * FROM sys_resource res WHERE res.resource_id IN(
                SELECT per.resource_id FROM sys_permission per WHERE per.role_id IN(
                SELECT ur.role_id FROM sys_user_role ur WHERE ur.user_id="73d29518-9b9d-45c8-a84a-c8df19d9bbd7"
)
)  ORDER BY sort_id ASC*/
        QueryWrapper<Resource> queryWrapper =new QueryWrapper<Resource>();
        queryWrapper
//                .gt("level",3)
                .inSql("resource_id","SELECT per.resource_id FROM sys_permission per WHERE per.role_id IN(\n" +
                        "SELECT ur.role_id FROM sys_user_role ur WHERE ur.user_id='"+user.getUserId()+"'\n" +
                        ")")
                .eq("application_id",app.getApplicationId())
                .orderByAsc("sort_id");

        List<Resource> listResource = resourceService.list(queryWrapper);//获取所有菜单
        appData.setApp(app);
        appData.setUser(user);
        appData.setMenu(buildMenuTree(convertDTO(listResource),null));
//        appData.setMenu(buildMenuTree(listResource,null));
        return appData;
    }

    private List<ResourceDTO> convertDTO(List<Resource> listResource){

        List<ResourceDTO> listResourceDTO = new ArrayList<>();
        for (Resource resource : listResource) {
            ResourceDTO resourceDTO=new ResourceDTO();
            resourceDTO.setId(resource.getResourceId());
            resourceDTO.setText(resource.getName());
            resourceDTO.setIcon(getExtend(resource.getExtend()).get("Icon")+"");
            resourceDTO.setLink(resource.getUri());
            resourceDTO.setTarget(null);
            resourceDTO.setI18n(null);
            resourceDTO.setGroup(false);
            resourceDTO.setHideInBreadcrumb(false);
            resourceDTO.setParentId(resource.getParentId());
            listResourceDTO.add(resourceDTO);
        }

        return listResourceDTO;
    }
    private List<ResourceDTO> buildMenuTree(List<ResourceDTO> menuList, String pid) {
        List<ResourceDTO> treeList = new ArrayList<>();
        menuList.forEach(menu -> {
            if (StringUtils.equals(pid, menu.getParentId())) {
                menu.setChildren(buildMenuTree(menuList, menu.getId()));
                treeList.add(menu);
            }
        });
        return treeList;
    }


    private Map getExtend(String jsonStirng){
       Map map= JSONObject.parseObject(jsonStirng);
       return map;
    }
}

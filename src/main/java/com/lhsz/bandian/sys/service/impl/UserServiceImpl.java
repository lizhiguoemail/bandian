package com.lhsz.bandian.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.DTO.*;
import com.lhsz.bandian.sys.DTO.query.QueryUserDTO;
import com.lhsz.bandian.sys.DTO.result.UserDTO;
import com.lhsz.bandian.sys.entity.*;
import com.lhsz.bandian.sys.mapper.RoleMapper;
import com.lhsz.bandian.sys.mapper.UserMapper;
import com.lhsz.bandian.sys.mapper.UserRoleMapper;
import com.lhsz.bandian.sys.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
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
    IUserRoleService userRoleService;
    @Autowired
    IRoleService roleService;
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
                .eq("type",1)
                .inSql("resource_id","SELECT per.resource_id FROM sys_permission per WHERE per.role_id IN(\n" +
                        "SELECT ur.role_id FROM sys_user_role ur WHERE ur.user_id='"+user.getUserId()+"'\n" +
                        ")")
                .eq("application_id",app.getApplicationId())
                .orderByAsc("sort_id");

        List<Resource> listResource = resourceService.list(queryWrapper);//获取所有菜单
        appData.setApp(convertUserDTO(app));
        appData.setUser(convertUserDTO(user));
        appData.setMenu(buildMenuTree(convertDTO(listResource),null));
//        appData.setMenu(buildMenuTree(listResource,null));
        return appData;
    }

    @Override
    public List<UserDTO> listQueryUserDTO(QueryUserDTO user) {
        List<UserDTO> list=new ArrayList<UserDTO>();
        QueryWrapper queryWrapper=new QueryWrapper();
        if(user.getUserName()!=null&&!"".equals(user.getUserName().trim())){
            queryWrapper.like("user_name",user.getUserName());
        }
        if(user.getPhoneNumber()!=null&&!"".equals(user.getPhoneNumber().trim())){
            queryWrapper.like("phone_number",user.getPhoneNumber());
        }
        if(user.getEmail()!=null&&!"".equals(user.getEmail().trim())){
            queryWrapper.like("email",user.getEmail());
        }
        List<User> listUser = userMapper.selectList(queryWrapper);
        List<UserRole>  userRoleList=userRoleService.list();
        List<Role> roleList=roleService.list();

        for (User user1 : listUser) {
            UserDTO userDTO=new UserDTO();
           /* BeanCopier beanCopier = BeanCopier.create(User.class, UserDTO.class, true);
            beanCopier.copy(user1, userDTO,null);*/
            BeanUtils.copyProperties(user1,userDTO);
            userDTO.setId(user1.getUserId());
            setRoles(userDTO,userRoleList,roleList);
            list.add(userDTO);
        }
       return list;
    }

    /*private  Set<String> getRoles (String uid,List<UserRole> roleUserList){
        Set<String> roleSet=new HashSet<>();
        for (UserRole userRole : roleUserList) {
            if(userRole.getUserId().equals(uid)){
                roleSet.add(userRole.getRoleId());
            }

        }
        return roleSet;
    }*/
    private void setRoles (UserDTO userDTO,List<UserRole> roleUserList,List<Role> roleList){
        Set<String> roleSet=new HashSet<>();
        Set<String> rolesDisplay =new HashSet<>();
        for (UserRole userRole : roleUserList) {
            if(userRole.getUserId().equals(userDTO.getId())){
                roleSet.add(userRole.getRoleId());
                for (Role role : roleList) {
                    if(role.getRoleId().equals(userRole.getRoleId()))
                    rolesDisplay.add(role.getName());
                }
            }

        }
        userDTO.setRoles(roleSet);
        userDTO.setRolesDisplay(rolesDisplay);
    }
    /*private  Set<String> getRolesDisplay (Set roles,List<Role> roleList){
        Set<String> rolesDisplay =new HashSet<>();
        for (Role role : roleList) {
            roles.
            rolesDisplay.add(role.getName());
        }
        return rolesDisplay;
    }*/

    private AppApplicationDTO convertUserDTO(Application app){
        AppApplicationDTO appDTO =new AppApplicationDTO(app);
//        appDTO.setName(app.getName());
//        appDTO.setDescription(app.getRemark());
        return appDTO;
    }
    private AppUserDTO convertUserDTO(User user){
        AppUserDTO userDTO =new AppUserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setAvatar(null);
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getUserName());
        userDTO.setNickName(user.getNickName());
        return userDTO;
    }
    private List<AppResourceDTO> convertDTO(List<Resource> listResource){

        List<AppResourceDTO> listResourceDTO = new ArrayList<>();
        for (Resource resource : listResource) {
            AppResourceDTO resourceDTO=new AppResourceDTO();
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
    private List<AppResourceDTO> buildMenuTree(List<AppResourceDTO> menuList, String pid) {
        List<AppResourceDTO> treeList = new ArrayList<>();
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

    /**
     * 用户详情
     * @param id
     * @return
     */
    public UserDTO getUserDTO(String id){
        User user=userMapper.selectById(id);
        UserDTO userDTO=new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        userDTO.setId(user.getUserId());
        List<UserRole>  userRoleList=userRoleService.list();
        List<Role> roleList=roleService.list();
        setRoles(userDTO,userRoleList,roleList);
        return userDTO;
    }
}

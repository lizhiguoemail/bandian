package com.lhsz.bandian.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.Exception.NoticeException;
import com.lhsz.bandian.jt.entity.JuryDept;
import com.lhsz.bandian.jt.entity.JuryDeptUser;
import com.lhsz.bandian.jt.entity.UserProfile;
import com.lhsz.bandian.jt.service.IJuryDeptService;
import com.lhsz.bandian.jt.service.IJuryDeptUserService;
import com.lhsz.bandian.jt.service.IUserProfileService;
import com.lhsz.bandian.jt.utils.JtString;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.DTO.AppApplicationDTO;
import com.lhsz.bandian.sys.DTO.AppData;
import com.lhsz.bandian.sys.DTO.AppResourceDTO;
import com.lhsz.bandian.sys.DTO.AppUserDTO;
import com.lhsz.bandian.sys.DTO.query.QueryUserDTO;
import com.lhsz.bandian.sys.DTO.result.JuryRegisterDTO;
import com.lhsz.bandian.sys.DTO.result.UserDTO;
import com.lhsz.bandian.sys.entity.*;
import com.lhsz.bandian.sys.mapper.UserMapper;
import com.lhsz.bandian.sys.service.*;
import com.lhsz.bandian.utils.Convert;
import com.lhsz.bandian.utils.RedisCache;
import com.lhsz.bandian.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@Slf4j
@Service

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserProfileService userProfileService;
    @Autowired
    private IJuryDeptService iJuryDeptService;
    @Autowired
    private IJuryDeptUserService iJuryDeptUserService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public User findByUsername(String username) {
        User user=null;
        try {
            LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
//            lambdaQueryWrapper.select(User::getUserId);
            lambdaQueryWrapper.eq(User::getNormalizedUserName,Convert.toUpperCase(username));
            user = super.getOne(lambdaQueryWrapper);
//            user = super.getOne(new QueryWrapper<User>().eq("user_name",username),false);
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User findByMobile(String mobile) {
        User user = getOne(new QueryWrapper<User>().eq("phone_number", mobile));

        return user;
    }
  @Override
    public User findByEmail(String mobile) {
        User user   = getOne(new QueryWrapper<User>().eq("normalized_email", Convert.toUpperCase(mobile)));
        return user;
    }


    @Override
    public User findByNameAndType(String username,Integer type) {
        User user=null;
        try {
            user = getOne(new QueryWrapper<User>().eq("user_name",username).ne("user_type",type));
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }
    @Override
    public Set<String> findPermissions(String uid) {
        Set<String> permissions = new HashSet<>();
      /*  permissions.add("sys:user:view");
        permissions.add("sys:user:add");
        permissions.add("sys:user:edit");
        permissions.add("sys:user:delete");*/
        QueryWrapper<Resource> queryWrapper =new QueryWrapper<Resource>();
        queryWrapper
                .eq("type",2)
                .inSql("resource_id","SELECT per.resource_id FROM sys_permission per WHERE per.role_id IN(\n" +
                        "SELECT ur.role_id FROM sys_user_role ur WHERE ur.user_id='"+uid+"'\n" +
                        ")");
        List<Resource> listResource = resourceService.list(queryWrapper);//获取所有菜单
        listResource.forEach(obj->permissions.add(obj.getUri()));
        return permissions;
    }
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int del(String id){
        QueryWrapper<UserRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        userRoleService.remove(queryWrapper);
        return baseMapper.deleteById(id);
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
                .eq("application_id",app.getApplicationId()).eq("enabled",1)
//                .orderByAsc("sort_id")
        ;

        List<Resource> listResource2 = resourceService.list(queryWrapper);//获取所有菜单
        //补充父级
        List<Resource> listResource=addParentResource(listResource2);
        appData.setApp(convertUserDTO(app));
        appData.setUser(convertUserDTO(user));
        appData.setMenu(buildMenuTree(convertDTO(listResource),null));
        return appData;
    }

    private List<Resource> addParentResource(List<Resource> listResource) {
        List<Resource> result=new ArrayList<>();
        //获取path, 根据PATH获取父级菜单ID，再从数据库查询全部数据
        StringBuffer ids=new StringBuffer();
        listResource.forEach(obj->{
            String path = obj.getPath();
            String rid=obj.getResourceId();
            String[] split = path.split(",");
            ids.append("'"+rid+"',");
            for (String s : split) {
                if(!rid.equals(s)){
                    ids.append("'"+s+"',");
                }
            }
        });
        if(ids.length() > 1){
            ids.deleteCharAt(ids.length()-1);
            QueryWrapper queryWrapper =new QueryWrapper();
            queryWrapper.inSql("resource_id",ids.toString());
            queryWrapper.orderByAsc("sort_id");
            log.debug("==========");
            log.debug(ids.toString());
            result=resourceService.list(queryWrapper);
        }
        return result;
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
        if(user.getUserType()!=null){
            queryWrapper.like("user_type",user.getUserType());
        }
        List<User> listUser = list(queryWrapper);
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
                    if(role.getRoleId().equals(userRole.getRoleId())) {
                        rolesDisplay.add(role.getName());
                    }
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
            resourceDTO.setExternalLink(resource.getExternalLink());
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
    @Override
    public UserDTO getUserDTO(String id){
        User user=getById(id);
        UserDTO userDTO=new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        userDTO.setId(user.getUserId());
        List<UserRole>  userRoleList=userRoleService.list();
        List<Role> roleList=roleService.list();
        setRoles(userDTO,userRoleList,roleList);
        return userDTO;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addUserAndRole(UserDTO userDTO) {
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        convert(user);
        save(user);
        Set<String> roles = userDTO.getRoles();
        if(roles!=null&&roles.size()>0){
            List<UserRole> listSaveUserRole =new ArrayList<>();
            for (String role : roles) {
                UserRole userRole=new UserRole();
                userRole.setRoleId(role);
                userRole.setUserId(user.getUserId());
                listSaveUserRole.add(userRole);
            }
            if(listSaveUserRole.size()>0){
                userRoleService.saveBatch(listSaveUserRole);
            }
        }

    }
   @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateUserAndRole(UserDTO userDTO) {
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);

        convert(user);
        UpdateWrapper updateWrapper=new UpdateWrapper();
       updateWrapper.eq("user_id",user.getUserId());
       if(userDTO.getEmail()==null||"".equals(userDTO.getEmail())){
           updateWrapper.set("email",null);
           updateWrapper.set("normalized_email",null);
       }
       if(userDTO.getPhoneNumber()==null||"".equals(userDTO.getPhoneNumber())) {
           updateWrapper.set("phone_number",null);
           updateWrapper.set("phone_number_confirmed", null);
       }
       if(userDTO.getRemark()==null||"".equals(userDTO.getRemark())) {
           updateWrapper.set("remark", null);
       }
       update(user,updateWrapper);
       //更新缓存 2020-8-11 去掉缓存更新
       tokenService.updateLoginUserToUser(user);
      /* QueryWrapper<UserRole> qw=new QueryWrapper<>();
       qw.eq("user_id",user.getUserId());


       userRoleService.remove(qw);*/
       userRoleService.trueToDelete(user.getUserId());
        Set<String> roles = userDTO.getRoles();
        List<UserRole> listSaveUserRole =new ArrayList<>();

        for (String role : roles) {
            UserRole userRole=new UserRole();
            userRole.setRoleId(role);
            userRole.setUserId(user.getUserId());
            listSaveUserRole.add(userRole);
        }
        userRoleService.saveBatch(listSaveUserRole);
    }

    @Override
    public UserDTO getUserDTO() {
        User user=tokenService.getLoginUserToUser();
        UserDTO userDTO=new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        userDTO.setId(user.getUserId());
        return userDTO;
    }

    @Override
    public boolean updateByIdForUserDTO(UserDTO userDTO) {
        User user=getById(userDTO.getId());
        user.setNickName(userDTO.getNickName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRemark(userDTO.getRemark());
        convert(user);
        //更新缓存 2020-8-11 去掉缓存更新
        tokenService.updateLoginUserToUser(user);
        return updateById(user);
    }

    @Override
    public User getUserByName(String userName) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("normalized_user_name",Convert.toUpperCase(userName));
        return getOne(queryWrapper);

    }

    @Override
    public User selectDeptIdUser(String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (id != null && !"".equals(id)) {
            queryWrapper.like("user_id", id);
        }
        User list = userMapper.selectById(id);
        return list;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void regist(UserDTO userDTO) {
        try{
            check(userDTO);
            User user=new User();
            BeanUtils.copyProperties(userDTO,user);
            convert(user);
            user.setUserType(JtString.USERTYPE_PORTAL);
            save(user);
            UserProfile userProfile = new UserProfile();
            userProfile.setUserId(user.getUserId());
            userProfile.setCertType("01");
            userProfile.setCertNo(userDTO.getCertNo());
            userProfile.setFullName(userDTO.getNickName());
            userProfile.setMobile(userDTO.getPhoneNumber());
            userProfile.setUsedName("");
            userProfile.setNation("");
            userProfile.setBirthday("");
            userProfile.setDomicile1("");
            userProfile.setDomicile2("");
            userProfile.setDomicile3("");
            userProfile.setChkStatus("00");
            userProfileService.save(userProfile);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void check(UserDTO userDTO) {

//        userMapper.selectList(new QueryWrapper<>().eq("user_name",userDTO.get))
        //判断用户名是否存在

        //判断邮箱是否被使用
        //判断手机号是否被使用
    }

    private void convert(User user){
        user.setNormalizedUserName(Convert.toUpperCase(user.getUserName()));
        user.setNormalizedEmail(Convert.toUpperCase(user.getEmail()));
        if(user.getPassword()!=null){
            user.setPasswordHash(SecurityUtils.encryptPassword(user.getPassword()));
            user.setPassword(null);
        }

    }

    @Override
    public void registerAdmin(JuryRegisterDTO juryRegisterDTO) {
        String userId = UUID.randomUUID().toString();
        String deptId = UUID.randomUUID().toString();
        User user=new User();
        BeanUtils.copyProperties(juryRegisterDTO,user);
        user.setUserId(userId);
        user.setNickName(juryRegisterDTO.getUserName());
        user.setEnabled(false);
        user.setUserType(3);
        convert(user);
        save(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId("2777e39f737aee6079dfd30ff47cece0");
        userRoleService.save(userRole);
        JuryDept juryDept = new JuryDept();
        juryDept.setDeptId(deptId);
        juryDept.setContactName(juryRegisterDTO.getContactName());
        juryDept.setContactTelephone(juryRegisterDTO.getPhoneNumber());
        juryDept.setDeptName(juryRegisterDTO.getDeptName());
        juryDept.setDeptType(Integer.parseInt(juryRegisterDTO.getDeptType()));
        iJuryDeptService.add(juryDept);
        JuryDeptUser juryDeptUser = new JuryDeptUser();
        juryDeptUser.setDeptId(deptId);
        juryDeptUser.setUserId(userId);
        iJuryDeptUserService.add(juryDeptUser);
    }

    @Override
    public String importUser(List<UserDTO> userList, boolean updateSupport) {

        if (userList == null || userList.size() == 0)
        {
            throw new NoticeException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = "111111";
        for (UserDTO user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                User u = findByUsername(user.getUserName());
                if (u==null)
                {
                    user.setPassword(password);
                    User saveuser=new User();
                    BeanUtils.copyProperties(user,saveuser);
                    convert(saveuser);
                    saveuser.setClientId("dcmis-admin");
                    save(saveuser);
                    successNum++;
                    successMsg.append("<pre>" + successNum + "、账号 " + user.getUserName() + " 导入成功</pre>");
                }
                else if (updateSupport)
                {
                    user.setUserId(u.getUserId());
                    BeanUtils.copyProperties(user,u);
                    convert(u);
                    updateById(u);
                    successNum++;
                    successMsg.append("<pre>" + successNum + "、账号 " + user.getUserName() + " 更新成功</pre>");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<pre>" + failureNum + "、账号 " + user.getUserName() + " 已存在</pre>");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<pre>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                failureMsg.append("</pre>");
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new NoticeException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}

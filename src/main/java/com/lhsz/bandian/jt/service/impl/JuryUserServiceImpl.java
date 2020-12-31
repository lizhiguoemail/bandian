package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.response.JuryUserDTO;
import com.lhsz.bandian.jt.entity.JuryDeptUser;
import com.lhsz.bandian.jt.service.IJuryDeptService;
import com.lhsz.bandian.jt.service.IJuryDeptUserService;
import com.lhsz.bandian.jt.service.IJuryUserService;
import com.lhsz.bandian.jt.utils.JtString;
import com.lhsz.bandian.sys.DTO.query.QueryUserDTO;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.entity.UserRole;
import com.lhsz.bandian.sys.mapper.UserMapper;
import com.lhsz.bandian.sys.service.IUserRoleService;
import com.lhsz.bandian.sys.service.IUserService;
import com.lhsz.bandian.utils.Convert;
import com.lhsz.bandian.utils.CopyUtils;
import com.lhsz.bandian.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
public class JuryUserServiceImpl extends ServiceImpl<UserMapper, User> implements IJuryUserService {

    @Autowired
    IJuryDeptUserService juryDeptUserService;
    @Autowired
    IJuryDeptService juryDeptService;
    @Autowired
    IJuryUserService juryUserService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addUserAndJury(JuryUserDTO userDTO) {
        User user=new User();
        CopyUtils.copyProperties(userDTO,user);
        convert(user);
        save(user);
        JuryDeptUser juryDeptUser=new JuryDeptUser();
        juryDeptUser.setDeptId(userDTO.getJuryDeptId());
        juryDeptUser.setUserId(user.getUserId());
        juryDeptUserService.save(juryDeptUser);
        UserRole userRole =new UserRole();
        userRole.setRoleId(JtString.ROLE_JURY_RID);
        userRole.setUserId(user.getUserId());
        userRoleService.save(userRole);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateUserAndJury(JuryUserDTO userDTO) {
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        convert(user);
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("user_id",user.getUserId());
        if(userDTO.getEmail()==null||"".equals(userDTO.getEmail())){
            updateWrapper.set("email",null);
            updateWrapper.set("normalized_email",null);
        }
        if(userDTO.getPhoneNumber()==null||"".equals(userDTO.getPhoneNumber())) {
            updateWrapper.set("phone_number", null);
            updateWrapper.set("phone_number_confirmed", null);
        }
        if(userDTO.getRemark()==null||"".equals(userDTO.getRemark())) {
            updateWrapper.set("remark", null);
        }
        update(user,updateWrapper);
      /*  QueryWrapper qw=new QueryWrapper();
        qw.eq("user_id",user.getUserId());
        juryDeptUserService.remove(qw);//删除用户与委员会关系*/
        juryDeptUserService.trueToDelete(user.getUserId());
        JuryDeptUser juryDeptUser=new JuryDeptUser();
        juryDeptUser.setDeptId(userDTO.getJuryDeptId());
        juryDeptUser.setUserId(user.getUserId());
        juryDeptUserService.save(juryDeptUser);//添加用户与委员会关系
    }

    @Override
    public List<JuryUserDTO> listQueryUserDTO(QueryUserDTO user) {
        QueryWrapper<QueryUserDTO> queryWrapper=new QueryWrapper<>();
        if(user.getUserName()!=null&&!"".equals(user.getUserName().trim())){
            queryWrapper.like("user_name",user.getUserName());
        }
        if(user.getPhoneNumber()!=null&&!"".equals(user.getPhoneNumber().trim())){
            queryWrapper.like("phone_number",user.getPhoneNumber());
        }
        if(user.getEmail()!=null&&!"".equals(user.getEmail().trim())){
            queryWrapper.like("email",user.getEmail());
        }
        if(user.getDeptId()!=null&&!"".equals(user.getDeptId())){
            queryWrapper.eq("jdu.dept_id",user.getDeptId());
        }
        queryWrapper.eq("user_type",3);
        queryWrapper.eq("su.is_deleted",0);
        return juryDeptService.listDTO(queryWrapper);
    }


    @Override
    public JuryUserDTO getUserDTO(String id) {
        User declare = baseMapper.selectById(id);
        JuryUserDTO juryUserDTO = new JuryUserDTO();
        BeanUtils.copyProperties(declare,juryUserDTO);
        juryUserDTO.setId(declare.getUserId());
        JuryDeptUser juryDeptUser = juryDeptUserService.selectByUserId(id);
        juryUserDTO.setJuryDeptId(juryDeptUser.getDeptId());
        return juryUserDTO;
    }
    
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void delUserAndJury(String id) {
        userService.removeById(id);
        QueryWrapper qw=new QueryWrapper();
        qw.eq("user_id",id);
        //删除用户与委员会关系
        juryDeptUserService.remove(qw);
        userRoleService.remove(qw);
    }

    private void convert(User user){
        user.setNormalizedUserName(Convert.toUpperCase(user.getUserName()));
        user.setNormalizedEmail(Convert.toUpperCase(user.getEmail()));
        if(user.getPassword()!=null){
            user.setPasswordHash(SecurityUtils.encryptPassword(user.getPassword()));
            user.setPassword(null);
        }

    }

}

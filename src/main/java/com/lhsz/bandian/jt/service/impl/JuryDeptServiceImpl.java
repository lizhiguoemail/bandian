package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.jt.DTO.response.JuryDeptDTO;
import com.lhsz.bandian.jt.DTO.response.JuryUserDTO;
import com.lhsz.bandian.jt.entity.JuryDept;
import com.lhsz.bandian.jt.entity.JuryDeptUser;
import com.lhsz.bandian.jt.mapper.JuryDeptMapper;
import com.lhsz.bandian.jt.mapper.JuryDeptUserMapper;
import com.lhsz.bandian.jt.service.IJuryDeptService;
import com.lhsz.bandian.jt.service.IJuryDeptUserService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.DTO.query.QueryUserDTO;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评委会主管部门 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class JuryDeptServiceImpl extends ServiceImpl<JuryDeptMapper, JuryDept> implements IJuryDeptService {

    private final JuryDeptMapper juryDeptMapper;
    private final JuryDeptUserMapper juryDeptUserMapper;
    private final IJuryDeptUserService iJuryDeptUserService;
    private final IUserService iUserService;
    private final TokenService tokenService;
    @Autowired
    public JuryDeptServiceImpl(JuryDeptMapper juryDeptMapper, JuryDeptUserMapper juryDeptUserMapper, IJuryDeptUserService iJuryDeptUserService, IUserService iUserService, TokenService tokenService) {
        this.juryDeptMapper = juryDeptMapper;
        this.juryDeptUserMapper = juryDeptUserMapper;
        this.iJuryDeptUserService = iJuryDeptUserService;
        this.iUserService = iUserService;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean add(JuryDept juryDept) {

        juryDept.setIsDeleted(0);
        return save(juryDept);
    }

    @Override
    public boolean add(JuryDeptDTO juryDept) {
        JuryDept dept = new JuryDept();
        BeanUtils.copyProperties(juryDept,dept);
        juryDept.setId(dept.getDeptId());
        String[] join = Joiner.on(",").join(juryDept.getCitys()).split(",");
        if (join.length > 0){
            dept.setProvince(join[0]);
            dept.setCity(join[1]);
            if(join.length > 2){
                dept.setCounty(join[2]);
            }
        }
        dept.setIsDeleted(0);
        return save(dept);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(JuryDept juryDept) {
        updateById(juryDept);
    }

    @Override
    public void update(JuryDeptDTO juryDept) {
        JuryDept juryDept1 = new JuryDept();
        BeanUtils.copyProperties(juryDept,juryDept1);
        String[] join = Joiner.on(",").join(juryDept.getCitys()).split(",");
        if (join.length > 0){
            juryDept1.setProvince(join[0]);
            juryDept1.setCity(join[1]);
            if(join.length > 2){
                juryDept1.setCounty(join[2]);
            }
        }
        updateById(juryDept1);
    }

    @Override
    public List<JuryDeptDTO> list(JuryDeptDTO juryDept) {
        List<JuryDeptDTO> juryDeptDTOS;
        User loginUserToUser = null;
        try {
            loginUserToUser = tokenService.getLoginUserToUser();
        } catch (NullPointerException e) {
            return juryDeptMapper.selectListJury(juryDept, null);
        }
        if(loginUserToUser.getUserType() == 3) {
            juryDeptDTOS = juryDeptMapper.selectListJury(juryDept, loginUserToUser.getUserId());
        }else {
            juryDeptDTOS = juryDeptMapper.selectListJury(juryDept, null);
        }
        return juryDeptDTOS;
    }


    @Override
    public JuryDeptDTO selectById(String id) {
        JuryDept juryDept = juryDeptMapper.selectById(id);
        JuryDeptDTO juryDeptDTO = new JuryDeptDTO();
        BeanUtils.copyProperties(juryDept,juryDeptDTO);
        juryDeptDTO.setId(juryDept.getDeptId());
        return juryDeptDTO;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int del(String id) {
        juryDeptUserMapper.trueToDeleteByDeptId(id);//删除主管评委表和用户的关系
        return juryDeptMapper.deleteById(id);
    }

    @Override
//    @Cacheable(value = JtCacheString.LISTALLBYDEPTTYPE,key = "#deptType")
    public List<SelectDTO> listAllBydeptType(Integer deptType) {
        List<SelectDTO> result = new ArrayList<>();
        QueryWrapper<JuryDept> queryWrapper = new QueryWrapper<>();
        if(deptType!=null){
            queryWrapper.eq("dept_type",deptType);
        }
        list(queryWrapper).forEach(obj->result.add(new SelectDTO(obj.getDeptId(),obj.getDeptName(),obj.getDeptId(),false)));
        return result;
    }
    @Override
    public List<JuryDept> listBydeptType(Integer deptType) {
        QueryWrapper<JuryDept> queryWrapper = new QueryWrapper<>();
        if(deptType!=null){
            queryWrapper.eq("dept_type",deptType);
        }
        return list(queryWrapper);
    }

    @Override
    public JuryDept getByUserId(String deptId,List<JuryDept> cacheList) {
        JuryDept result=null;
        if(cacheList.size()>0) {
            for (JuryDept obj : cacheList) {
                if (obj.getDeptId().equals(deptId)) {
                    result = obj;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public List<JuryUserDTO> listDTO(QueryWrapper<QueryUserDTO> queryWrapper) {
        return juryDeptMapper.listDTO(queryWrapper);
    }

    /**
     * 查询待审核评委会
     * @param juryDept 传递部门类型
     * @return JuryDeptDTO
     */
    @Override
    public List<JuryDeptDTO> listDisableDTO(JuryDept juryDept) {
        return juryDeptMapper.listDisableDTO(juryDept);
    }

    @Override
    public JuryDept selectByUserId(String id) {
        JuryDeptUser juryDeptUser = iJuryDeptUserService.selectByUserId(id);
        QueryWrapper<JuryDept> juryDeptQueryWrapper = new QueryWrapper<>();
        if (id!=null&&!"".equals(id.trim())){
            juryDeptQueryWrapper.eq("dept_id",juryDeptUser.getDeptId());
        }
        return juryDeptMapper.selectOne(juryDeptQueryWrapper);
    }

    @Override
    public List<JuryDept> search(JuryDept juryDept) {
        QueryWrapper<JuryDept> juryDeptQueryWrapper = new QueryWrapper<>();
        if (juryDept.getProvince()!=null&&!"".equals(juryDept.getProvince().trim())){
            juryDeptQueryWrapper.eq("province",juryDept.getProvince());
        }
        if (juryDept.getCity()!=null&&!"".equals(juryDept.getCity().trim())){
            juryDeptQueryWrapper.eq("city",juryDept.getCity());
        }
        if (juryDept.getCounty()!=null&&!"".equals(juryDept.getCounty().trim())){
            juryDeptQueryWrapper.eq("county",juryDept.getCounty());
        }

        return juryDeptMapper.selectList(juryDeptQueryWrapper);
    }

    @Override
    public boolean checkJury(String deptId, boolean checkState) {
        // 通过就启用该部门唯一的账户 拒绝就逻辑删除评委会记录和部门--用户关系记录
        if(checkState){
            List<JuryDeptUser> juryDeptUsers = iJuryDeptUserService.selectById(deptId);
            String userId = juryDeptUsers.get(0).getUserId();
            User user = new User();
            user.setUserId(userId);
            user.setEnabled(checkState);
            return iUserService.updateById(user);
        } else {
            int del = del(deptId);
            return true;
        }

    }
}

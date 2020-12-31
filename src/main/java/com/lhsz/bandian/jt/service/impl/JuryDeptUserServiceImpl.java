package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.entity.JuryDeptUser;
import com.lhsz.bandian.jt.mapper.JuryDeptUserMapper;
import com.lhsz.bandian.jt.service.IJuryDeptUserService;
import com.lhsz.bandian.jt.utils.JtCacheString;
import com.lhsz.bandian.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评委会主管部门用户 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class JuryDeptUserServiceImpl extends ServiceImpl<JuryDeptUserMapper, JuryDeptUser> implements IJuryDeptUserService {
    @Autowired
    private  JuryDeptUserMapper juryDeptUserMapper;
    @Autowired
    private RedisCache redisCache;

    @Override
    public boolean add(JuryDeptUser juryDeptUser) {
        boolean saveState = save(juryDeptUser);
        removeCache();
        return saveState;
    }

    @Override
    public void update(JuryDeptUser juryDeptUser) {
        updateById(juryDeptUser);
        removeCache();
    }

    @Override
    public List<JuryDeptUser> list(JuryDeptUser juryDeptUser) {
        QueryWrapper<JuryDeptUser> juryDeptUserQueryWrapper = new QueryWrapper<>();
        if (juryDeptUser.getUserId()!=null&&!"".equals(juryDeptUser.getUserId().trim())){
            juryDeptUserQueryWrapper.like("user_id",juryDeptUser.getUserId());
        }
        if (juryDeptUser.getDeptId()!=null&&!"".equals(juryDeptUser.getDeptId().trim())){
            juryDeptUserQueryWrapper.like("dept_id",juryDeptUser.getDeptId());
        }

        List<JuryDeptUser> list = juryDeptUserMapper.selectList(juryDeptUserQueryWrapper);

        return list;
    }

    @Override
    public List<JuryDeptUser> selectById(String id) {
        QueryWrapper<JuryDeptUser> queryWrapper=new QueryWrapper();
        if(id!=null&&!"".equals(id))
        {
            queryWrapper.like("dept_id",id);
        }
        List<JuryDeptUser> list1 = list(queryWrapper);
        return list1;
    }

    @Override
    public int del(String id) {
        removeCache();
        return juryDeptUserMapper.deleteById(id);
    }

    @Override
    public List<JuryDeptUser> listAll() {
        List<JuryDeptUser> cacheList = redisCache.getCacheList(JtCacheString.JURYDEPTUSERLIST);
        if(cacheList==null||cacheList.size()==0){
            cacheList=list();
            redisCache.setCacheList(JtCacheString.JURYDEPTUSERLIST,cacheList);
        }
        return cacheList;
    }

    @Override
    public JuryDeptUser getJuryDeptUser(String userId, List<JuryDeptUser> cacheList) {
        JuryDeptUser result=null;
        if(cacheList.size()>0) {
            for (JuryDeptUser juryDeptUser : cacheList) {
                if (juryDeptUser.getUserId().equals(userId)) {
                    result = juryDeptUser;
                    break;
                }
            }
        }
        return result;
    }
    public void removeCache(){
        redisCache.deleteObject(JtCacheString.JURYDEPTUSERLIST);
    }

    @Override
    public JuryDeptUser selectByUserId(String id) {
        QueryWrapper<JuryDeptUser> juryDeptUserQueryWrapper = new QueryWrapper<>();
        if (id!=null&&!"".equals(id.trim())){
            juryDeptUserQueryWrapper.eq("user_id",id);
        }
        JuryDeptUser juryDeptUser = juryDeptUserMapper.selectOne(juryDeptUserQueryWrapper);
        return juryDeptUser;
    }

    @Override
    public void trueToDelete(String userId) {
        juryDeptUserMapper.trueToDelete(userId);
    }
}

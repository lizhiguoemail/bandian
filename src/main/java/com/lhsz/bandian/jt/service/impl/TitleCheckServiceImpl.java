package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.entity.TitleCheck;
import com.lhsz.bandian.jt.mapper.TitleCheckMapper;
import com.lhsz.bandian.jt.service.ITitleCheckService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 申报职称审核 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-08-13
 */
@Service
public class TitleCheckServiceImpl extends ServiceImpl<TitleCheckMapper, TitleCheck> implements ITitleCheckService {
    @Override
    public void add(TitleCheck titleCheck) {
        String checkId = UUID.randomUUID().toString();
        titleCheck.setCheckId(checkId);
        titleCheck.setIsDeleted(0);
        save(titleCheck);
    }

    @Override
    public void updateByApplyId(TitleCheck titleCheck) {
        QueryWrapper<TitleCheck> queryWrapper = new QueryWrapper<>();
        if(titleCheck.getApplyId()!=null&&!"".equals(titleCheck.getApplyId())){
            queryWrapper.eq("apply_id", titleCheck.getApplyId());
        }
        if(titleCheck.getDeptType()!=null){
            queryWrapper.eq("dept_type", titleCheck.getDeptType());
        }

        update(titleCheck,queryWrapper);
    }

    @Override
    public int countByApplyId(String applyId) {
        QueryWrapper<TitleCheck> queryWrapper = new QueryWrapper<>();
        if(applyId!=null&&!"".equals(applyId)){
            queryWrapper.eq("apply_id", applyId);
        }
        return count(queryWrapper);
    }

    @Override
    public boolean delByApplyId(String applyId, int deptType) {
        QueryWrapper<TitleCheck> queryWrapper = new QueryWrapper<>();
        if(applyId!=null&&!"".equals(applyId)){
            queryWrapper.eq("apply_id", applyId);
        }
        if(deptType == 1 || deptType == 2){
            queryWrapper.eq("dept_type", deptType);
        }
        return remove(queryWrapper);
    }
}

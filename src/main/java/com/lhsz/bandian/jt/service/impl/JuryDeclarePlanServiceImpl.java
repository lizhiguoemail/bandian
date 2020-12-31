package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.response.JuryDeclarePlanDTO;
import com.lhsz.bandian.jt.entity.JuryDeclarePlan;
import com.lhsz.bandian.jt.mapper.JuryDeclarePlanMapper;
import com.lhsz.bandian.jt.service.IJuryDeclarePlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 评委会申报计划 服务实现类
 * </p>
 *
 * @author zhusenlin
 * @since 2020-08-03
 */
@Service
public class JuryDeclarePlanServiceImpl extends ServiceImpl<JuryDeclarePlanMapper, JuryDeclarePlan> implements IJuryDeclarePlanService {

    private final JuryDeclarePlanMapper juryDeclarePlanMapper;

    @Autowired
    public JuryDeclarePlanServiceImpl(JuryDeclarePlanMapper juryDeclarePlanMapper) {
        this.juryDeclarePlanMapper = juryDeclarePlanMapper;
    }

    @Override
    public boolean add(JuryDeclarePlan juryDeclarePlan) {
        juryDeclarePlan.setPlanId(UUID.randomUUID().toString());
        juryDeclarePlan.setIsDeleted(0);
        boolean saveState = save(juryDeclarePlan);
        return saveState;
    }

    @Override
    public void update(JuryDeclarePlan juryDeclarePlan) {
        updateById(juryDeclarePlan);
    }

    @Override
    public List<JuryDeclarePlanDTO> list(JuryDeclarePlan juryDeclarePlan) {


        List<JuryDeclarePlanDTO> juryDeclarePlanDTOS = juryDeclarePlanMapper.selectMapperList(juryDeclarePlan);

        return juryDeclarePlanDTOS;

    }

    @Override
    public JuryDeclarePlanDTO selectById(String id) {
        JuryDeclarePlanDTO juryDeclarePlanDTO = juryDeclarePlanMapper.selectDTO(id);
        return juryDeclarePlanDTO;
    }

    @Override
    public int del(String id) {
        return juryDeclarePlanMapper.deleteById(id);
    }

    @Override
    public List<JuryDeclarePlanDTO> search(String declareCategory,String declareSpecialty,String declareTitle) {
        QueryWrapper<JuryDeclarePlan> flowQueryWrapper = new QueryWrapper<>();
        if (declareCategory != null && !"".equals(declareCategory.trim())) {
            flowQueryWrapper.eq("declare_category",declareCategory);
        }
        if (declareSpecialty != null && !"".equals(declareSpecialty.trim())) {
            flowQueryWrapper.eq("declare_specialty",declareSpecialty);
        }
        if (declareTitle != null && !"".equals(declareTitle.trim())) {
            flowQueryWrapper.eq("declare_title",declareTitle);
        }
        List<JuryDeclarePlan> list = juryDeclarePlanMapper.selectList(flowQueryWrapper);
        ArrayList<JuryDeclarePlanDTO> juryDeclarePlanDTOS = new ArrayList<>();
        for (JuryDeclarePlan juryDeclarePlan1 : list) {
            JuryDeclarePlanDTO juryDeclarePlanDTO = new JuryDeclarePlanDTO();
            BeanUtils.copyProperties(juryDeclarePlan1, juryDeclarePlanDTO);
            juryDeclarePlanDTO.setId(juryDeclarePlan1.getPlanId());
            juryDeclarePlanDTOS.add(juryDeclarePlanDTO);
        }
        return juryDeclarePlanDTOS;
    }

}

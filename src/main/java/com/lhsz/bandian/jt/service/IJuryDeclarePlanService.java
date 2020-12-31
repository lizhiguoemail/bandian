package com.lhsz.bandian.jt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.jt.DTO.response.JuryDeclarePlanDTO;
import com.lhsz.bandian.jt.entity.JuryDeclarePlan;

import java.util.List;

/**
 * <p>
 * 评委会申报计划 服务类
 * </p>
 *
 * @author zhusenlin
 * @since 2020-08-03
 */
public interface IJuryDeclarePlanService extends IService<JuryDeclarePlan> {

    boolean add(JuryDeclarePlan juryDeclarePlan);

    void update(JuryDeclarePlan juryDeclarePlan);

    List<JuryDeclarePlanDTO> list(JuryDeclarePlan juryDeclarePlan);

    JuryDeclarePlanDTO selectById(String id);

    int del(String id);

    List<JuryDeclarePlanDTO> search(String declareCategory,String declareSpecialty,String declareTitle);


}

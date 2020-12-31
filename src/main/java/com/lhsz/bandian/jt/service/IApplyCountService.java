package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.TitleApplyCountDTO;
import com.lhsz.bandian.jt.DTO.request.TitleApplyJtStateCountDTO;
import com.lhsz.bandian.jt.entity.ApplyCount;
import com.lhsz.bandian.jt.entity.TitleApply;

import java.util.HashMap;
import java.util.List;

/**
 * @author zhusenlin
 * Date on 2020/8/27  9:43
 */
public interface IApplyCountService {
    /**
     * 查询近七天统计申请状态数据
     * @return 近七天统计申请状态数据
     */
    List<ApplyCount> getTotalStateChart();

    /**
     * 查询登录用户部门的待审核、不通过和通过数量
     * @param titleApply userId
     * @return 登录用户部门的待审核、不通过和通过数量
     */
    TitleApplyCountDTO getStateCount(TitleApply titleApply);

    /**
     * 查询 userId 下业绩库各个状态总条数
     * @return userId 下业绩库个状态总条数
     * @param userId 用户ID
     */
    HashMap<String, Integer> getJtTotalCount(String userId);

    /**
     * 查询 userId 下业绩库各项目各个状态条数
     * @return userId 下业绩库各项目各个状态条数
     * @param userId 用户ID
     */
    TitleApplyJtStateCountDTO getJtStateCount(String userId);
}

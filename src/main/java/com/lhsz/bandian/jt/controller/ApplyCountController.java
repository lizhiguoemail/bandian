package com.lhsz.bandian.jt.controller;

import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.jt.DTO.request.TitleApplyCountDTO;
import com.lhsz.bandian.jt.DTO.request.TitleApplyJtStateCountDTO;
import com.lhsz.bandian.jt.entity.ApplyCount;
import com.lhsz.bandian.jt.entity.TitleApply;
import com.lhsz.bandian.jt.service.IApplyCountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 申报统计数据 前端控制器
 *
 * @author zhusenlin
 * Date on 2020/8/27  8:55
 */
@RestController
@RequestMapping("/applyCount")
public class ApplyCountController {
    private final IApplyCountService applyCountService;

    @Autowired
    public ApplyCountController(IApplyCountService applyCountService) {
        this.applyCountService = applyCountService;
    }

    /**
     * 查询近七天统计申请状态数据
     */
    @ApiOperation(value = "查询近七天统计申请状态数据")
    @GetMapping("/getTotalStateChart")
    public HttpResult getTotalStateChart(){
        List<ApplyCount> totalStateChart = applyCountService.getTotalStateChart();
        return HttpResult.ok(totalStateChart);
    }

    /**
     * 查询登录用户部门的待审核、不通过和通过数量
     */
    @ApiOperation(value = "查询登录用户部门的待审核、不通过和通过数量")
    @GetMapping("/stateCount")
    public HttpResult getStateCount(TitleApply titleApply){
        TitleApplyCountDTO stateCount = applyCountService.getStateCount(titleApply);
        return HttpResult.ok(stateCount);
    }

    /**
     * 查询 userId 下业绩库各个状态总条数
     * @param userId 用户Id
     * @return userId 下业绩库个状态总条数
     */
    @ApiOperation(value = "查询 userId 下业绩库各个状态总条数")
    @GetMapping("/jtTotalCount")
    public HttpResult getJtTotalCount(String userId){
        HashMap<String, Integer> jtStateCount = applyCountService.getJtTotalCount(userId);
        return HttpResult.ok(jtStateCount);
    }

    /**
     * 查询 userId 下业绩库各项目各个状态条数
     * @param userId 用户Id
     * @return userId 下业绩库各项目各个状态条数
     */
    @ApiOperation(value = "查询 userId 下业绩库各项目各个状态条数")
    @GetMapping("/JtStateCount")
    public HttpResult getJtStateCount(String userId){
        TitleApplyJtStateCountDTO jtStateCount = applyCountService.getJtStateCount(userId);
        return HttpResult.ok(jtStateCount);
    }
}

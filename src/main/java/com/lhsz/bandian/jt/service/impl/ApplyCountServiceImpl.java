package com.lhsz.bandian.jt.service.impl;

import com.lhsz.bandian.jt.DTO.request.TitleApplyCountDTO;
import com.lhsz.bandian.jt.DTO.request.TitleApplyJtStateCountDTO;
import com.lhsz.bandian.jt.entity.ApplyCount;
import com.lhsz.bandian.jt.entity.JuryDeptUser;
import com.lhsz.bandian.jt.entity.TitleApply;
import com.lhsz.bandian.jt.service.IApplyCountService;
import com.lhsz.bandian.jt.service.IJuryDeptUserService;
import com.lhsz.bandian.jt.service.ITitleApplyService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 申报统计数据 服务实现类
 *
 * @author zhusenlin
 * Date on 2020/8/27  9:45
 */
@Service
public class ApplyCountServiceImpl implements IApplyCountService {

    private final ITitleApplyService iTitleApplyService;
    private final TokenService tokenService;
    private final IJuryDeptUserService iJuryDeptUserService;
    private final RedisCache redisCache;

    @Autowired
    public ApplyCountServiceImpl(ITitleApplyService iTitleApplyService, TokenService tokenService, IJuryDeptUserService iJuryDeptUserService, RedisCache redisCache) {
        this.iTitleApplyService = iTitleApplyService;
        this.tokenService = tokenService;
        this.iJuryDeptUserService = iJuryDeptUserService;
        this.redisCache = redisCache;
    }

    @Override
    public List<ApplyCount> getTotalStateChart() {
        List<ApplyCount> totalStateChart = iTitleApplyService.getTotalStateChart();

        /* 关闭缓存
        List<ApplyCount> totalStateChart = Convert.castList(redisCache.getCacheObject("TotalStateChart"), ApplyCount.class);
        if (totalStateChart == null || totalStateChart.size() <= 0) {
            totalStateChart = iTitleApplyService.getTotalStateChart();
            redisCache.setCacheObject("TotalStateChart", totalStateChart, 3, TimeUnit.MINUTES);
        }
        */
        return totalStateChart;
    }

    @Override
    public TitleApplyCountDTO getStateCount(TitleApply titleApply) {
        User user = tokenService.getLoginUserToUser();
        if(user.getUserType() == 3){
            JuryDeptUser juryDeptUser = iJuryDeptUserService.selectByUserId(user.getUserId());
            titleApply.setChkOfficeId(juryDeptUser.getDeptId());
        }
        TitleApplyCountDTO stateCount = iTitleApplyService.getStateCount(titleApply);
        return stateCount;
    }

    @Override
    public HashMap<String, Integer> getJtTotalCount(String userId) {
        return iTitleApplyService.getJtTotalStateCount(userId);
    }

    @Override
    public TitleApplyJtStateCountDTO getJtStateCount(String userId) {
        return iTitleApplyService.getJtStateCount(userId);
    }
}

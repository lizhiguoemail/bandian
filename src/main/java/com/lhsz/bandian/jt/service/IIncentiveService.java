package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.IncentiveAddDTO;
import com.lhsz.bandian.jt.DTO.request.IncentiveDetailDTO;
import com.lhsz.bandian.jt.DTO.request.IncentiveUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.IncentiveDTO;
import com.lhsz.bandian.jt.entity.Incentive;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 奖惩情况 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IIncentiveService extends IService<Incentive> {

    boolean add(Incentive incentive);

    boolean add(IncentiveAddDTO incentive);

    void update(Incentive incentive);

    void update(IncentiveUpdateDTO incentive);

    List<IncentiveDTO> list(Incentive incentive);

    IncentiveDTO selectById(String id);

    IncentiveDetailDTO detailById(String id);

    int del(String id);

}

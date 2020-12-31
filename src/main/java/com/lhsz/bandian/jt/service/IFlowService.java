package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.jt.DTO.response.FlowDTO;
import com.lhsz.bandian.jt.entity.Flow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 申报流程 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IFlowService extends IService<Flow> {

    boolean add(Flow flow);

    void update(Flow flow);

    List<FlowDTO> list(Flow flow);

    FlowDTO selectById(String id);

    int del(String id);

    List<SelectDTO> listAll();

    String selectFlowValueByUserId(String userId);
}

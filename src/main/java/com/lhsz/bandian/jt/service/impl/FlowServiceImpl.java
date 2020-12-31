package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.jt.DTO.response.FlowDTO;
import com.lhsz.bandian.jt.entity.Flow;
import com.lhsz.bandian.jt.mapper.FlowMapper;
import com.lhsz.bandian.jt.service.IFlowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 申报流程 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow> implements IFlowService {

    private final FlowMapper flowMapper;

    @Autowired
    public FlowServiceImpl(FlowMapper flowMapper) {
        this.flowMapper = flowMapper;
    }


    @Override
    public boolean add(Flow flow) {
        flow.setIsDeleted(0);
        boolean saveState = save(flow);
        return saveState;
    }

    @Override
    public void update(Flow flow) {
        updateById(flow);
    }

    @Override
    public List<FlowDTO> list(Flow flow) {
        QueryWrapper<Flow> flowQueryWrapper = new QueryWrapper<>();
        if (flow.getFlowName()!=null&&!"".equals(flow.getFlowName().trim())){
            flowQueryWrapper.like("flow_name",flow.getFlowName());
        }
        if (flow.getEnabled()!=null){
            if(flow.getEnabled() == true) {
                flowQueryWrapper.eq("enabled", 1);
            }else if (flow.getEnabled() == false){
                flowQueryWrapper.eq("enabled", 0);
            }
        }

        List<Flow> list = flowMapper.selectList(flowQueryWrapper);

        ArrayList<FlowDTO> flowDTOS = new ArrayList<>();
        for (Flow flow1 : list) {
            FlowDTO flowDTO = new FlowDTO();
            BeanUtils.copyProperties(flow1,flowDTO);
            flowDTO.setId(flow1.getFlowId());
            flowDTOS.add(flowDTO);
        }
        return flowDTOS;

    }

    @Override
    public FlowDTO selectById(String id) {
        Flow flow = flowMapper.selectById(id);
        FlowDTO flowDTO = new FlowDTO();
        BeanUtils.copyProperties(flow,flowDTO);
        flowDTO.setId(flow.getFlowId());
        return flowDTO;

    }

    @Override
    public int del(String id) {
        return flowMapper.deleteById(id);
    }

    @Override
    public List<SelectDTO> listAll() {
        List<SelectDTO> result=new ArrayList<>();
        list().forEach(obj-> {
          SelectDTO selectDTO = new SelectDTO();
            selectDTO.setText(obj.getFlowName());
            selectDTO.setValue(obj.getFlowId());
            result.add(selectDTO);
        });
        return result;
    }

    @Override
    public String selectFlowValueByUserId(String userId) {
        return flowMapper.selectFlowValueByUserId(userId);
    }
}

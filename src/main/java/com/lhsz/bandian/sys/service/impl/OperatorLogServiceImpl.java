package com.lhsz.bandian.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.sys.DTO.result.OperatorLogDTO;
import com.lhsz.bandian.sys.entity.OperatorLog;
import com.lhsz.bandian.sys.mapper.OperatorLogMapper;
import com.lhsz.bandian.sys.service.IOperatorLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Service
public class OperatorLogServiceImpl extends ServiceImpl<OperatorLogMapper, OperatorLog> implements IOperatorLogService {

    @Autowired
    private OperatorLogMapper operatorLogMapper;
    @Override
    public List<OperatorLogDTO> list(OperatorLog operatorLog) {
        ArrayList<OperatorLogDTO> operatorLogDTOS = new ArrayList<>();
        QueryWrapper queryWrapper=new QueryWrapper();
        if (operatorLog.getTitle()!=null&&!"".equals(operatorLog.getTitle().trim())){
            queryWrapper.like("title",operatorLog.getTitle());
        }
        if (operatorLog.getBusinessType()!=null){
            queryWrapper.like("business_type",operatorLog.getBusinessType());
        }
        if (operatorLog.getOperatorTime()!=null){
            queryWrapper.like("operator_time",operatorLog.getOperatorTime());
        }
        List<OperatorLog> list = operatorLogMapper.selectList(queryWrapper);
        for (OperatorLog operatorLog1 : list) {
            OperatorLogDTO operatorLogDTO = new OperatorLogDTO();
            BeanUtils.copyProperties(operatorLog1,operatorLogDTO);
            operatorLogDTO.setId(operatorLog1.getLogId());
            operatorLogDTOS.add(operatorLogDTO);
        }
        return operatorLogDTOS;
    }

    @Override
    public OperatorLogDTO selectById(String id) {
        OperatorLog operatorLog = operatorLogMapper.selectById(id);
        OperatorLogDTO operatorLogDTO = new OperatorLogDTO();
        BeanUtils.copyProperties(operatorLog,operatorLogDTO);
        operatorLogDTO.setId(operatorLog.getLogId());
        return operatorLogDTO;
    }

    @Override
    public int del(String id) {
        return operatorLogMapper.deleteById(id);
    }
}

package com.lhsz.bandian.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.sys.DTO.result.OperatorLogDTO;
import com.lhsz.bandian.sys.entity.OperatorLog;

import java.util.List;

/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
public interface IOperatorLogService extends IService<OperatorLog> {
    List<OperatorLogDTO> list(OperatorLog operatorLog);
    OperatorLogDTO selectById(String id);

    int del(String id);

}

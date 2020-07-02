package com.lhsz.bandian.sys.service.impl;

import com.lhsz.bandian.sys.entity.OperatorLog;
import com.lhsz.bandian.sys.mapper.OperatorLogMapper;
import com.lhsz.bandian.sys.service.IOperatorLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@Service
public class OperatorLogServiceImpl extends ServiceImpl<OperatorLogMapper, OperatorLog> implements IOperatorLogService {

}

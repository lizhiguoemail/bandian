package com.lhsz.bandian.sys.service.impl;

import com.lhsz.bandian.sys.entity.LoginLog;
import com.lhsz.bandian.sys.mapper.LoginLogMapper;
import com.lhsz.bandian.sys.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

}

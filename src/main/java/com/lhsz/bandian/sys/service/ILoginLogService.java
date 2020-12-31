package com.lhsz.bandian.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.sys.DTO.result.LoginLogDTO;
import com.lhsz.bandian.sys.entity.LoginLog;

import java.util.List;

/**
 * <p>
 * 登录日志 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
public interface ILoginLogService extends IService<LoginLog> {
    List<LoginLogDTO> list(LoginLog loginLog);
    LoginLogDTO selectById(String id);

    int del(String id);

}

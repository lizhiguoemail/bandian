package com.lhsz.bandian.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.sys.DTO.result.LoginLogDTO;
import com.lhsz.bandian.sys.entity.LoginLog;
import com.lhsz.bandian.sys.mapper.LoginLogMapper;
import com.lhsz.bandian.sys.service.ILoginLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public List<LoginLogDTO> list(LoginLog loginLog) {
        ArrayList<LoginLogDTO> loginLogDTOS = new ArrayList<>();
        QueryWrapper queryWrapper=new QueryWrapper();
        if (loginLog.getLoginName()!=null&&!"".equals(loginLog.getLoginName().trim())){
            queryWrapper.like("login_name",loginLog.getLoginName());
        }
        if (loginLog.getLoginTime()!=null){
            queryWrapper.like("login_time",loginLog.getLoginTime());
        }
        List<LoginLog> list = loginLogMapper.selectList(queryWrapper);
        for (LoginLog log : list) {
            LoginLogDTO loginLogDTO = new LoginLogDTO();
            BeanUtils.copyProperties(log,loginLogDTO);
            loginLogDTO.setId(log.getLogId());
            loginLogDTOS.add(loginLogDTO);
        }
        return loginLogDTOS;
    }

    @Override
    public LoginLogDTO selectById(String id) {
        LoginLog log = loginLogMapper.selectById(id);
        LoginLogDTO loginLogDTO = new LoginLogDTO();
        BeanUtils.copyProperties(log,loginLogDTO);
        loginLogDTO.setId(log.getLogId());
        return loginLogDTO;
    }

    @Override
    public int del(String id) {
        return loginLogMapper.deleteById(id);
    }
}

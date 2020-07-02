package com.lhsz.bandian.sys.service.impl;

import com.lhsz.bandian.sys.entity.Application;
import com.lhsz.bandian.sys.mapper.ApplicationMapper;
import com.lhsz.bandian.sys.service.IApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用程序 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {

}

package com.lhsz.bandian.sys.service.impl;

import com.lhsz.bandian.sys.entity.Permission;
import com.lhsz.bandian.sys.mapper.PermissionMapper;
import com.lhsz.bandian.sys.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}

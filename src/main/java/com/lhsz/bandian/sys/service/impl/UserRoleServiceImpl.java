package com.lhsz.bandian.sys.service.impl;

import com.lhsz.bandian.sys.entity.UserRole;
import com.lhsz.bandian.sys.mapper.UserRoleMapper;
import com.lhsz.bandian.sys.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;
    @Override
    public void trueToDelete(String uid) {
        userRoleMapper.trueToDelete(uid);
    }
}

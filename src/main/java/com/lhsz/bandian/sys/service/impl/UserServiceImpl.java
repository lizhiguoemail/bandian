package com.lhsz.bandian.sys.service.impl;

import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.mapper.UserMapper;
import com.lhsz.bandian.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User findByUsername(String username) {
        User user = new User();
//        user.setId(1L);
        user.setUserId(1L);
        user.setUserName(username);
        String password = new BCryptPasswordEncoder().encode("123");
        user.setPassword(password);
        return user;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String> permissions = new HashSet<>();
        permissions.add("sys:user:view");
        permissions.add("sys:user:add");
        permissions.add("sys:user:edit");
        permissions.add("sys:user:delete");
        return permissions;
    }
}

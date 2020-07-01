package com.lhsz.bandian.sys.service.impl;

import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.mapper.UserMapper;
import com.lhsz.bandian.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

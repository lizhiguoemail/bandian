package com.lhsz.bandian.security;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lhsz.bandian.Exception.NoticeException;
import com.lhsz.bandian.jt.utils.JtString;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;



/**
 * 用户登录认证信息查询
 *
 * 自定义的 UserDetailsService，从我们的用户服务 UserService 中获取用户和权限信息
 * 一般而言，定制 UserDetailsService 就可以满足大部分需求了，在 UserDetailsService 满足不了我们的需求的时候考虑定制 AuthenticationProvider。
 *
 * 如果直接定制UserDetailsService ，而不自定义 AuthenticationProvider，可以直接在配置文件 WebSecurityConfig 中这样配置。
 *
 * @Override
 * public void configure(AuthenticationManagerBuilder auth) throws Exception {
 *     // 指定自定义的获取信息获取服务
 *     auth.userDetailsService(userDetailsService)
 * }
 * @author Louis
 * @date Jun 29, 2019
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
//        User user = userService.findByNameAndType(username, JtString.USERTYPE_PORTAL);
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }else{
           //
            if(user!=null){
                if(!user.getEnabled()){
                    throw new NoticeException("该用户未启用");
                }
                UpdateWrapper<User> userWrapper = new UpdateWrapper<>();
                userWrapper.set("login_count",user.getLoginCount()+1);
                userWrapper.eq("user_id",user.getUserId());
                userService.update(userWrapper);
                log.info(user.getUserName()+"登录次数+1");
            }
        }

        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        Set<String> permissions = userService.findPermissions(user.getUserId());
        return new LoginUser(user, permissions);

    }
}

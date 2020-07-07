package com.lhsz.bandian.security;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


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
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    public LoginUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        Set<String> permissions = userService.findPermissions(username);
        List<GrantedAuthority> grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
//        return new JwtUserDetails(user, permissions);
//        return new JwtUserDetails(username, user.getPassword(), grantedAuthorities);
        return  new LoginUser(user, permissions);

    }
}

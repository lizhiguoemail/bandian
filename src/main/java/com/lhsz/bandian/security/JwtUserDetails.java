package com.lhsz.bandian.security;

import java.util.Collection;
import java.util.Set;

import com.lhsz.bandian.sys.entity.User;
import org.springframework.security.core.GrantedAuthority;

/**
 * 安全用户模型
 * @author lizhiguo
 * @date 2020-7-2 16:20:44
 */
//public class JwtUserDetails extends User {
public class JwtUserDetails extends LoginUser {

    private static final long serialVersionUID = 1L;


    public JwtUserDetails(User user, Set<String> permissions) {
        super(user, permissions);
    }
}

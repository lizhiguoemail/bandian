//package com.lhsz.bandian.security;
//
//import java.util.Collection;
//import java.util.Set;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
///**
// * 安全用户模型
// * @author lizhiguo
// * @date 2020-7-2 16:20:44
// */
//public class JwtUserDetails extends User {
//
//    private static final long serialVersionUID = 1L;
//
//
//    public JwtUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//    }
//
//    public JwtUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    }
//}

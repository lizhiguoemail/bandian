package com.lhsz.bandian.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * 权限封装
 * @author lizhiguo
 * @date 2020-7-7 17:56:58
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
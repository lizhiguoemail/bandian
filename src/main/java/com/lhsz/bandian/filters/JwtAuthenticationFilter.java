package com.lhsz.bandian.filters;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhsz.bandian.security.SecurityService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 登录认证检查过滤器
 * @author lizhiguo
 * @date 2020-7-2 16:33:46
 */
@Component
//public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityService securityService;
//    @Autowired
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取token, 并检查登录状态
        securityService.checkAuthentication(request);
        chain.doFilter(request, response);
    }

}
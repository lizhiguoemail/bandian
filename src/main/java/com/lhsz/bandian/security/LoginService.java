package com.lhsz.bandian.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lizhiguo
 * 2020/7/6 11:18
 */
@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    /**
     * 系统登录认证
     * @param request
     * @param username
     * @param password
     * @return
     */
    public  JwtAuthenticatioToken login(HttpServletRequest request, String username, String password) {
        JwtAuthenticatioToken token = new JwtAuthenticatioToken(username, password);
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 执行登录认证过程
        Authentication authentication = authenticationManager.authenticate(token);
        // 认证成功存储认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌并返回给客户端
//        token.setToken(JwtTokenUtils.generateToken(authentication));
        token.setToken(tokenService.createToken(authentication));
        return token;
    }

}

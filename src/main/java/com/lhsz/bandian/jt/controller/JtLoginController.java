/*
package com.lhsz.bandian.jt.controller;

import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.LoginBean;
import com.lhsz.bandian.security.JwtAuthenticatioToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

*/
/**
 * @author lizhiguo
 * 2020/7/20 9:04
 *//*

public class JtLoginController {
    @PostMapping(value = "/login")
    public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();
        String clientId = loginBean.getClientId();

        // 系统登录认证
        JwtAuthenticatioToken token = loginService.login(request, username, password,clientId);

        return HttpResult.ok(token);
    }
}
*/

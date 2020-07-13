package com.lhsz.bandian.controller;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.lhsz.bandian.pojo.HttpResult;
import com.lhsz.bandian.pojo.LoginBean;
import com.lhsz.bandian.pojo.page.ResponseResult;
import com.lhsz.bandian.security.JwtAuthenticatioToken;
import com.lhsz.bandian.security.LoginService;
import com.lhsz.bandian.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class LoginController extends BaseController{
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private RedisCache redisCache;
    @Autowired
    private LoginService loginService;
    /**
     * 登录接口
     * 其实 Spring Security 的登录认证过程只需调用 AuthenticationManager 的 authenticate(Authentication authentication) 方法，
     * 最终返回认证成功的 Authentication 实现类并存储到SpringContexHolder 上下文即可，这样后面授权的时候就可以从 SpringContexHolder 中获取登录认证信息，并根据其中的用户信息和权限信息决定是否进行授权。

     注意：如果使用此登录控制器触发登录认证，需要禁用登录认证过滤器，即将 WebSecurityConfig 中的以下配置项注释即可，否则访问登录接口会被过滤拦截，执行不会再进入此登录接口，大家根据使用习惯二选一即可。
     // 开启登录认证流程过滤器，如果使用LoginController的login接口, 需要注释掉此过滤器，根据使用习惯二选一即可
     http.addFilterBefore(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);


     */
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

package com.lhsz.bandian.config.security;

import com.lhsz.bandian.filters.JwtAuthenticationFilter;
import com.lhsz.bandian.security.AuthenticationEntryPointImpl;
import com.lhsz.bandian.security.JwtAuthenticationProvider;
//import com.lhsz.bandian.filters.JwtLoginFilter;
import com.lhsz.bandian.security.LogoutSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/*@EnableWebSecurity的作用：

​ 1: 加载WebSecurityConfiguration配置类, 配置安全认证策略。

​ 2: 加载AuthenticationConfiguration, 配置了认证信息。


*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义登录身份认证组件
        auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * antMatchers: ant的通配符规则
         * ?	匹配任何单字符
         * *	匹配0或者任意数量的字符，不包含"/"
         * **	匹配0或者更多的目录，包含"/"
         * anyRequest          |   匹配所有请求路径
         * access              |   SpringEl表达式结果为true时可以访问
         * anonymous           |   匿名可以访问
         * denyAll             |   用户不能访问
         * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
         * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
         * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
         * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
         * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
         * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
         * permitAll           |   用户可以任意访问
         * rememberMe          |   允许通过remember-me登录的用户访问
         * authenticated       |   用户登录后可访问
         */

        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrfAuthenticationEntryPointImpl
        http.cors().and().csrf().disable()
                //登录后,访问没有权限处理类
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //匿名访问,没有权限的处理类
                //禁用session
                .authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                // 登录URL
                .antMatchers("/login").permitAll()
                // swagger
                .antMatchers("/swagger**/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/profile/**").permitAll()
                .antMatchers("/common/download**").permitAll()
                .antMatchers("/common/download/resource**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                .antMatchers("/druid/**").permitAll()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated()
                // 配置登录认证 ,我试了下可以用，登录成功又跳到登录页了，应该有一个地方可以配置登录成功跳转路径，你也可以试试
//                .and().formLogin().loginProcessingUrl("/login")//使用内置的登录验证过滤器，默认实现为 UsernamePasswordAuthenticationFilter
        ;
        // 退出登录处理器
        http.logout().logoutSuccessHandler(logoutSuccessHandler);
        // 开启登录认证流程过滤器
        // 开启登录认证流程过滤器，如果使用LoginController的login接口, 需要注释掉此过滤器，二选一
//        http.addFilterBefore(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        // 访问控制时登录状态检查过滤器
//        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
 /*   一般而言，定制 UserDetailsService 就可以满足大部分需求了，在 UserDetailsService 满足不了我们的需求的时候考虑定制 AuthenticationProvider。

    如果直接定制UserDetailsService ，而不自定义 AuthenticationProvider，可以直接在配置文件 WebSecurityConfig 中这样配置。
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 指定自定义的获取信息获取服务
        auth.userDetailsService(userDetailsService)
    }*/
}

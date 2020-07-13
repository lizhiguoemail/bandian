package com.lhsz.bandian.security;

import com.alibaba.fastjson.JSON;
import com.lhsz.bandian.pojo.HttpResult;
import com.lhsz.bandian.pojo.HttpStatus;
import com.lhsz.bandian.utils.HttpUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 * @author lizhiguo
 * 2020/7/6 16:01
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String msg = "请求访问：{"+httpServletRequest.getRequestURI()+"}，认证失败，无法访问系统资源"+e.getMessage();
        HttpUtils.write(httpServletResponse, HttpResult.fail(msg));
    }
}

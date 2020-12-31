package com.lhsz.bandian.security;

import com.lhsz.bandian.config.properties.BandianProperties;
import com.lhsz.bandian.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 *
 * @author lizhiguo
 * 2020/7/6 16:01
 */
@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        String msg = "请求访问：{" + httpServletRequest.getRequestURI() + "}，认证失败，请重新登陆！，" + e.getMessage();
        if(BandianProperties.isSingleLogin){
            msg="登陆已注销！如有疑问，请联系管理员。";
        }
        log.error(msg + e.getMessage());
        httpServletResponse.setStatus(401);
        HttpUtils.writeFail(httpServletResponse, msg);

    }
}

package com.lhsz.bandian.AOP;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lhsz.bandian.security.JwtAuthenticatioToken;
import com.lhsz.bandian.security.LoginUser;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.LoginLog;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.ILoginLogService;
import com.lhsz.bandian.sys.service.IUserService;
import com.lhsz.bandian.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 记录登录人信息，登录成功次数+1，登录失败失败次数+1
 */
@Slf4j
@Aspect
@Component
public class LoginAspect {
    @Autowired
    ILoginLogService loginLogService;
    @Autowired
    TokenService tokenService;
    @Autowired
    IUserService userService;
    @Pointcut("execution(public * com.lhsz.bandian.security.LoginService.login(..))")
    public void login(){}
    @AfterReturning(value = "login()", returning="returnValue")
    public void returnLogin2(JoinPoint jp, Object returnValue) {
        Object[] args = jp.getArgs();
        saveLog((String) args[1], returnValue);
    }
    private void saveLog(String username, Object rvt) {
        try{
            if(rvt instanceof JwtAuthenticatioToken){
                JwtAuthenticatioToken jwtAuthenticatioToken = (JwtAuthenticatioToken)rvt;
                String token=jwtAuthenticatioToken.getAccess_token();
                LoginUser loginUser = tokenService.getLoginUser(token);
                LoginLog loginLog=new LoginLog();
                loginLog.setLoginName(loginUser.getUsername());
                loginLog.setIpAddress(loginUser.getIpaddr());
                loginLog.setLoginLocation(loginUser.getLoginLocation());
                loginLog.setOperatingSystem(loginUser.getOs());
                loginLog.setPromptMsg("登陆成功");
                loginLog.setLoginTime(DateUtils.LongConvertLocalDateTime(loginUser.getLoginTime()));
                loginLog.setBrowser(loginUser.getBrowser());
                loginLog.setStatus(0);
                log.info("登录成功！");
                loginLogService.save(loginLog);
                log.info("保存登录日志");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            loginFail(username, e);
            log.error("记录登录日志报错"+e.getMessage());
        }
    }

    //
    @AfterThrowing(value = "login()", throwing="ex")
    public void exceptionLogin(JoinPoint jp,Throwable ex){
        try {
            Object[] args = jp.getArgs();
            log.error("登陆异常");
            loginFail((String) args[1], ex);

        }catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private void loginFail(String username, Throwable ex) {
        LoginUser loginUser =new LoginUser();
        if(username!=null&&!"".equals(username)){
            User user = userService.getUserByName(username);
            loginUser.setUser(user);
            tokenService.setUserAgent(loginUser);
            LoginLog loginLog=new LoginLog();
            loginLog.setLoginName(username);
            loginLog.setIpAddress(loginUser.getIpaddr());
            loginLog.setLoginLocation(loginUser.getLoginLocation());
            loginLog.setOperatingSystem(loginUser.getOs());
            loginLog.setPromptMsg(ex.getLocalizedMessage());
            loginLog.setLoginTime(DateUtils.LongConvertLocalDateTime(System.currentTimeMillis()));
            loginLog.setBrowser(loginUser.getBrowser());
            loginLog.setStatus(1);
            loginLogService.save(loginLog);
            log.info("保存登录日志");
            if(user!=null){
                UpdateWrapper<User> userWrapper = new UpdateWrapper<>();
                userWrapper.set("access_failed_count",user.getAccessFailedCount()+1);
                userWrapper.eq("user_name",username);
                userService.update(userWrapper);
                log.info(user.getUserName()+"登录失败次数+1");
            }
        }
    }

}

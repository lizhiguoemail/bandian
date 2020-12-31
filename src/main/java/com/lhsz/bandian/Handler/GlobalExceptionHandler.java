package com.lhsz.bandian.Handler;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.Exception.NoticeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author lizhiguo
 * 2020/7/16 10:55
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 基础异常
     */
    @ExceptionHandler(RuntimeException.class)
    public HttpResult baseException(RuntimeException e)
    {
        log.error(e.getLocalizedMessage());
        e.printStackTrace();
        return HttpResult.error(e.getLocalizedMessage());
    }



    @ExceptionHandler(NoHandlerFoundException.class)
    public HttpResult handlerNoFoundException(Exception e)
    {
        log.error(e.getMessage(), e);
        HttpStatus aa = HttpStatus.NOT_FOUND;
        return HttpResult.error(HttpStatus.NOT_FOUND.value(), "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public HttpResult handleAuthorizationException(AccessDeniedException e)
    {
        log.error(e.getMessage());
        return HttpResult.error(HttpStatus.FORBIDDEN.value(), "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public HttpResult handleAccountExpiredException(AccountExpiredException e)
    {
        log.error(e.getMessage(), e);
        return HttpResult.error(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public HttpResult handleUsernameNotFoundException(UsernameNotFoundException e)
    {
        log.error(e.getMessage(), e);
        return HttpResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public HttpResult handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        return HttpResult.error("系统异常，请联系管理员");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public HttpResult validatedBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return HttpResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return HttpResult.error(message);
    }
 /**
     * 自定义验证异常
     */
    @ExceptionHandler(NoticeException.class)
    public Object validExceptionHandler(NoticeException e)
    {
        log.error(e.getMessage(), e);
        return HttpResult.error(e.getMessage());
    }


}

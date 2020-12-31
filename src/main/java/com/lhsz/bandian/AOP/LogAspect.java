package com.lhsz.bandian.AOP;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.security.LoginUser;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.OperatorLog;
import com.lhsz.bandian.sys.entity.Resource;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IOperatorLogService;
import com.lhsz.bandian.sys.service.IResourceService;
import com.lhsz.bandian.utils.DateUtils;
import com.lhsz.bandian.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Autowired
    IOperatorLogService operatorLogService;
    @Autowired
    TokenService tokenService;
    @Autowired
    IResourceService resourceService;
    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.lhsz.bandian.AOP.OperLog)")
    public void operLogPoinCut() {
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
   /* @Pointcut("execution(* com.lhsz.bandian.*.controller..*.*(..))")
    public void operExceptionLogPoinCut() {
    }*/

    /**
     * 添加业务逻辑方法切入点
     */
    @Pointcut("execution(* com.lhsz.bandian.*.controller.*.add*(..))")
    public void insertCell() {}

    /**
     * 修改业务逻辑方法切入点
     */
    @Pointcut("execution(* com.lhsz.bandian.*.controller.*.update*(..))")
    public void updateCell() {}

    /**
     * 删除业务逻辑方法切入点
     */
    @Pointcut("execution(* com.lhsz.bandian.*.controller.*.delete*(..))")
    public void deleteCell() {}

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param object      返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "object")
    public void saveOperLog(JoinPoint joinPoint, Object object) {
    /*    // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);*/

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        OperLog opLog = method.getAnnotation(OperLog.class);
        addLog(joinPoint,object,opLog);
    }

    /**
     * 添加操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     */
    @AfterReturning(value = "insertCell()", argNames = "joinPoint,object", returning = "object")
    public void insertLog(JoinPoint joinPoint, Object object)  {
        addLog(joinPoint,object);
    }
    /**
     * 修改操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     */
    @AfterReturning(value = "updateCell()", argNames = "joinPoint,object", returning = "object")
    public void updateLog(JoinPoint joinPoint, Object object)  {
        addLog(joinPoint,object);
    }
    /**
     * 删除操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     */
    @AfterReturning(value = "deleteCell()", argNames = "joinPoint,object", returning = "object")
    public void deleteLog(JoinPoint joinPoint, Object object)  {
        addLog(joinPoint,object);

    }
    private void addLog(JoinPoint joinPoint, Object object){
        addLog(joinPoint,object,null);
    }
    private void addLog(JoinPoint joinPoint, Object object,OperLog opLog){
        try {
            OperatorLog operatorLog =new OperatorLog();
            //获取操作用户
            LoginUser loginUser = tokenService.getLoginUser();
            String requestURI = HttpUtils.getHttpServletRequest().getRequestURI();
            String[] split = requestURI.split("/");
            String prenturi="/"+split[2]+"/"+ split[3];
            QueryWrapper queryWrapper =new QueryWrapper();
            queryWrapper.eq("uri",prenturi);
            Resource resource = resourceService.getOne(queryWrapper);
            User user = loginUser.getUser();
            // 判断参数
            if (joinPoint.getArgs() == null) {// 没有参数
                return;
            }
            // 获取方法名
            String methodName = joinPoint.getSignature().getName();
            log.debug("methodName: "+methodName);
            // 获取操作内容
            String opContent = optionContent2(joinPoint.getArgs(), methodName);
            log.debug("opContent: "+opContent);
            if (opLog != null) {
                operatorLog.setTitle(opLog.operModul());// 操作模块
                operatorLog.setBusinessType(opLog.operType().getValue());// 操作类型
                operatorLog.setRemark(opLog.operDesc());// 操作描述

            }else{
                if(resource!=null)
                {
                    operatorLog.setTitle(resource.getName());//模块名称
                }else{
                    operatorLog.setTitle("未知模块");//
                }
                operatorLog.setBusinessType(convertTypeByMethodName(methodName));//业务类型（0其它 1新增 2修改 3删除）
            }
            operatorLog.setMethod(methodName);
            operatorLog.setRequestMethod(HttpUtils.getHttpServletRequest().getMethod());//请求方式
            operatorLog.setOperatorType(convertOperType(user.getUserType()));//操作类别（0其它 1后台用户 2手机端用户） 可以根据用户类型来判断
            operatorLog.setOperatorName(user.getUserName());
            operatorLog.setOperatorUrl(HttpUtils.getHttpServletRequest().getRequestURI());//请求URL
            operatorLog.setOperatorIp(loginUser.getIpaddr());//主机地址
            operatorLog.setOperatorLocation(loginUser.getLoginLocation());
            operatorLog.setOperatorParams(opContent);
            if(object instanceof HttpResult){
                HttpResult httpResult =(HttpResult) object;
                operatorLog.setJsonResult(JSON.toJSONString(httpResult));
                operatorLog.setStatus(httpResult.getCode()==1?0:1);//操作状态（0正常 1异常）
                if(httpResult.getCode()==0){
                    operatorLog.setErrorMsg(httpResult.getMessage());
                }
            }
            operatorLog.setOperatorTime(DateUtils.LongConvertLocalDateTime(System.currentTimeMillis()));
            operatorLog.setOperatingSystem(loginUser.getOs());
            operatorLog.setBrowser(loginUser.getBrowser());
            operatorLogService.save(operatorLog);
        }catch (Exception e){
            log.error("日志记录失败"+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * //操作类别（0其它 1后台用户 2手机端用户）
     * @param userType
     * @return
     */
    private Integer convertOperType(Integer userType) {

        return userType==2 ? 0 : 1;
    }

    /**
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
     *
     * @param args
     * @param mName
     * @return
     */
    public String optionContent(Object[] args, String mName) {
        if (args == null) {
            return null;
        }
        StringBuffer rs = new StringBuffer();
        rs.append(mName);
        String className = null;
        int index = 1;
        // 遍历参数对象
        for (Object info : args) {
            // 获取对象类型
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数" + index + "，类型:" + className + "，值:");
            // 获取对象的所有方法
            Method[] methods = info.getClass().getDeclaredMethods();
            // 遍历方法，判断get方法
            for (Method method : methods) {
                String methodName = method.getName();
                // 判断是不是get方法
                if (methodName.indexOf("get") == -1) {// 不是get方法
                    continue;// 不处理
                }
                Object rsValue = null;
                try {
                    // 调用get方法，获取返回值
                    rsValue = method.invoke(info);
                } catch (Exception e) {
                    continue;
                }
                // 将值加入内容中
                rs.append("(" + methodName + ":" + rsValue + ")");
            }
            rs.append("]");
            index++;
        }
        return rs.toString();
    }
    /**
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
     *
     * @param args
     * @param mName
     * @return
     */
    public String optionContent2(Object[] args, String mName) {
        if (args == null) {
            return null;
        }
        StringBuffer rs = new StringBuffer();
        rs.append("{");//1
//        rs.append(mName+":");
        String className = null;
        int index = 1;
        // 遍历参数对象
        for (Object info : args) {
            // 获取对象类型
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
//            rs.append("[参数" + index + "，类型:" + className + "，值:");

            rs.append(className+":{");//2

            // 获取对象的所有方法
            Method[] methods = info.getClass().getDeclaredMethods();
            // 遍历方法，判断get方法
            for (Method method : methods) {
                String methodName = method.getName();
                // 判断是不是get方法
                if (methodName.indexOf("get") == -1) {// 不是get方法
                    continue;// 不处理
                }
                Object rsValue = null;
                try {
                    // 调用get方法，获取返回值
                    rsValue = method.invoke(info);
                } catch (Exception e) {
                    continue;
                }
                // 将值加入内容中
                if(rsValue!=null) {
                    rs.append("" + methodName + ":" + rsValue + ",");
                }
            }
            rs.append("}");//2
            index++;
        }
        rs.append("}");//1
        return rs.toString();
    }
    /**
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
     *
     * @param args
     * @param mName
     * @return
     */
    public String optionContent3(Object[] args, String mName) {
        if (args == null) {
            return null;
        }
        StringBuffer rs = new StringBuffer();
        rs.append("{");//1
//        rs.append(mName+":");
        String className = null;
        int index = 1;
        // 遍历参数对象
        for (Object info : args) {
            rs.append(info);
        }
        rs.append("}");//1
        return rs.toString();
    }
    private String convertMethodName(String method){
      try {
          if(method.contentEquals("add")){
              return "新建";
          }
          else if(method.contentEquals("update")){
              return "修改";
          }
          else if(method.contentEquals("delete")){
              return "删除";
          }
          else{
              return method;
          }
      }catch (Exception e)
      {
          return method;
      }
    }

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     * @param method
     * @return
     */
    private Integer convertTypeByMethodName(String method){
        try {
            StringBuffer str3 = new StringBuffer( "method");
            if(method.indexOf("add")!=-1){
                return 1;
            }
            else if(method.indexOf("update")!=-1){
                return 2;
            }
            else if(method.indexOf("delete")!=-1){
                return 3;
            }

            else{
                return 0;
            }
        }catch (Exception e)
        {
            return 0;
        }
    }
}

package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.sys.DTO.result.LoginLogDTO;
import com.lhsz.bandian.sys.entity.LoginLog;
import com.lhsz.bandian.sys.service.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 登录日志 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/sys/login-log")
public class LoginLogController extends BaseController {
    @Autowired
    private ILoginLogService iLoginLogService;
    /**
     * 查询登陆日志
     */
    @GetMapping()
    public HttpResult listDictType(LoginLog log) {
        startPage();
        List<LoginLogDTO> list = iLoginLogService.list(log);
        return HttpResult.ok(getDataTable(list));
    }
    /**
     * 登陆日志详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        LoginLogDTO loginLogDTO = iLoginLogService.selectById(id);
        return HttpResult.ok(loginLogDTO);


    }

    /**
     * 根据ID删除登陆日志
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            int del = iLoginLogService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

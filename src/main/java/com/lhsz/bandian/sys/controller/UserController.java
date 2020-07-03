package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.pojo.page.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.lhsz.bandian.controller.BaseController;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController {
    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value="/findAll")
    public ResponseResult findAll() {
//        return HttpResult.ok("the findAll service is called success.");
        return ResponseResult.ok().render("the findAll service is called success.");
    }

    @PreAuthorize("hasAuthority('sys:user:edit')")
    @GetMapping(value="/edit")
    public ResponseResult edit() {
        return ResponseResult.ok().render("the edit service is called success.");
    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @GetMapping(value="/delete")
    public ResponseResult delete() {
        return ResponseResult.ok().render("the delete service is called success.");
    }
}

package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.pojo.HttpResult;
import com.lhsz.bandian.pojo.page.ResponseResult;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.lhsz.bandian.controller.BaseController;

import java.util.List;

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
    @Autowired
    private UserServiceImpl userService;

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value="/findAll")
    public ResponseResult findAll() {
//        return HttpResult.ok("the findAll service is called success.");
      List<User> userlist= userService.list();
        return ResponseResult.ok().render(userlist);
    }

    @PreAuthorize("hasAuthority('sys:user:edit')")
    @GetMapping(value="/edit")
    public HttpResult edit() {
        return HttpResult.ok("the edit service is called success.");
    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @GetMapping(value="/delete")
    public HttpResult delete() {
        return HttpResult.ok("the delete service is called success.");
    }
    @PutMapping("/add")
    public ResponseResult add(User user){
        userService.save(user);
        return ResponseResult.ok();
    }
}

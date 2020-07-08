package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.pojo.HttpResult;
import com.lhsz.bandian.pojo.HttpStatus;
import com.lhsz.bandian.pojo.page.ResponseResult;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.impl.UserServiceImpl;
import com.lhsz.bandian.utils.Convert;
import com.lhsz.bandian.utils.SecurityUtils;
import com.lhsz.bandian.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping(value="/edit")
    public HttpResult edit(User user) {
        convert(user);
        if(userService.updateById(user)){
            return HttpResult.ok();
        }
        else{
            return HttpResult.error();
        }
    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @GetMapping(value="/delete")
    public HttpResult delete() {
        return HttpResult.ok("the delete service is called success.");
    }

    /**
     * 添加用户
      * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:add')")
    @PostMapping("/add")
    public HttpResult add(User user){
        convert(user);
        check(user);
        if(userService.save(user)){
            return HttpResult.ok();
        }
        else{
            return HttpResult.error();
        }
    }

    private void check(User user) {
        //判断用户名是否存在
        //判断邮箱是否被使用
        //判断手机号是否被使用
    }

    private void convert(User user){
        if(StringUtils.isNotEmpty(user.getUserName())){
            user.setNormalizedUserName(Convert.toUpperCase(user.getUserName()));
        }
        if(StringUtils.isNotEmpty(user.getEmail())) {
            user.setNormalizedEmail(Convert.toUpperCase(user.getEmail()));
        }
        if(StringUtils.isNotEmpty(user.getPassword())){
            user.setPasswordHash(SecurityUtils.encryptPassword(user.getPassword()));
            user.setPassword(null);
        }

    }

}

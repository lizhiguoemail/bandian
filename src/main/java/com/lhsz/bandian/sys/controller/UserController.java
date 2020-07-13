package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.pojo.HttpResult;
import com.lhsz.bandian.pojo.page.ResponseResult;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.Application;
import com.lhsz.bandian.sys.entity.Resource;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.DTO.AppData;
import com.lhsz.bandian.sys.service.IApplicationService;
import com.lhsz.bandian.sys.service.IResourceService;
import com.lhsz.bandian.sys.service.IUserService;
import com.lhsz.bandian.utils.Convert;
import com.lhsz.bandian.utils.SecurityUtils;
import com.lhsz.bandian.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@RestController
@RequestMapping("/systems/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;


    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value="/findAll")
    public ResponseResult findAll() {
        startPage();
//        return HttpResult.ok("the findAll service is called success.");
        List<User> userlist= userService.list();
        return ResponseResult.ok().render(userlist);
    }

    @PostMapping(value="/list")
    public HttpResult list(){

        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_name","bandian");
        Collection<User> list = userService.listByMap(columnMap);

        return HttpResult.ok(list);
    }

    @PreAuthorize("hasAuthority('sys:user:edit')")
    @PutMapping(value="/edit")
    public HttpResult edit(User user) {
        if(user==null||"".equals(user.getVersion())||user.getVersion()==null){
            return HttpResult.error("请传入version");
        }
        if(userService.updateById(user)){
            return HttpResult.succee();
        }
        else{
            return HttpResult.error();
        }

    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @DeleteMapping(value="/delete")
    public HttpResult delete(String uid) {
        if(userService.del(uid)==1) {
            return HttpResult.succee();
        }else{
            return HttpResult.fail();
        }

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
    @GetMapping("/app-data")
    public HttpResult app_data(){

        AppData appData=userService.getApp_data();//返回数据封装

        return HttpResult.ok(appData);
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

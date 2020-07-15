package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.sys.DTO.query.QueryUserDTO;
import com.lhsz.bandian.sys.DTO.result.UserDTO;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.DTO.AppData;
import com.lhsz.bandian.sys.service.IUserService;
import com.lhsz.bandian.utils.Convert;
import com.lhsz.bandian.utils.SecurityUtils;
import com.lhsz.bandian.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/systems")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     * 添加用户
     * @param user
     * @return
     */
//    @PreAuthorize("hasAuthority('sys:user:add')")
    @PostMapping("/user")
    public HttpResult add(@RequestBody User user){
        convert(user);
        check(user);
        if(userService.save(user)){
            return HttpResult.ok();
        }
        else{
            return HttpResult.error();
        }
    }

    /**
     * 获取菜单数据
     * @return
     */
    @GetMapping("user/app-data")
    public HttpResult app_data(){

        AppData appData=userService.getApp_data();//返回数据封装

        return HttpResult.ok(appData);
    }

    /**
     * 用户列表
     * @param user
     * @return
     */
    @GetMapping("/user")
    public HttpResult listUser(QueryUserDTO user){
        startPage();
       List<UserDTO> listuser=userService.listQueryUserDTO(user);
       return HttpResult.ok(listuser);
    }

    /**
     * 用户详情
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public HttpResult showUser(@PathVariable("id") String id){
//        User user=userService.getById(id);
        UserDTO user=userService.getUserDTO(id);
        return HttpResult.ok(user);
    }

    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/user/{id}")
    public HttpResult deleteUser(@PathVariable("id") String id){
        if(userService.del(id)!=1){
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    @GetMapping("/user/pwd/{id}")
    public HttpResult updatePwd(@PathVariable("id") String id){
        String pwd="111111";
        User user=new User();
        user.setPasswordHash(SecurityUtils.encryptPassword(pwd));
        if(userService.updateById(user)){
            return HttpResult.succee();
        }else {
            return HttpResult.fail();
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

/*
测试
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
*/

}

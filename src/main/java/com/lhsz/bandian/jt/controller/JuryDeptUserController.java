package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 评委会主管部门用户 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/jury-dept-user")
public class JuryDeptUserController extends BaseController {
//2020-8-13 lizhiguo  这个是关系表
//    private final IJuryDeptUserService juryDeptUserService;
//
//    @Autowired
//    public JuryDeptUserController(IJuryDeptUserService juryDeptUserService) {
//        this.juryDeptUserService = juryDeptUserService;
//    }
//
//    /**
//     * 查询主评委会主管部门用户
//     */
//    @GetMapping()
//    public HttpResult list(JuryDeptUser juryDeptUser) {
//        try {
//            startPage();
//            return HttpResult.ok(getDataTable(juryDeptUserService.list(juryDeptUser)));
//        } catch (Exception e) {
//            log.debug("查询主评委会主管部门用户异常",e);
//            return HttpResult.error("系统内部异常");
//        }
//    }
//
//
//    /**
//     * 添加评委会主管部门用户
//     */
//    @PostMapping()
//    public HttpResult add(@RequestBody JuryDeptUser juryDeptUser){
//        try {
//            // 普通用户不能访问该接口
//            User user=tokenService.getLoginUserToUser();
//            if(user.getUserType() == 2) {
//                return HttpResult.fail("无访问权限");
//            }
//            juryDeptUserService.add(juryDeptUser);
//            return HttpResult.ok();
//        } catch (Exception e) {
//            log.debug("添加评委会主管部门用户异常",e);
//            return HttpResult.error("系统内部异常");
//        }
//    }
//
//    /**
//     * 修改评委会主管部门用户
//     */
//    @PutMapping()
//    public HttpResult update(@RequestBody JuryDeptUser juryDeptUser) {
//        try {
//            // 普通用户不能访问该接口
//            User user=tokenService.getLoginUserToUser();
//            if(user.getUserType() == 2) {
//                return HttpResult.fail("无访问权限");
//            }
//            juryDeptUserService.update(juryDeptUser);
//            return HttpResult.ok();
//        } catch (Exception e) {
//            log.debug("修改评委会主管部门用户异常",e);
//            return HttpResult.error("系统内部异常");
//        }
//    }
//
//    /**
//     * 主持评委会主管部门用户
//     */
//    @GetMapping("/{id}")
//    public HttpResult selectById(@PathVariable("id") String id) {
//        try {
//            List<JuryDeptUser> juryDeptUser = juryDeptUserService.selectById(id);
//            return HttpResult.ok(juryDeptUser);
//        } catch (Exception e) {
//            log.debug("获取评委会主管部门用户异常",e);
//            return HttpResult.error("系统内部异常");
//        }
//    }

    /**
     * 根据ID删除评委会主管部门用户
     */
   /*
    2020-8-13 lizhiguo  这个是关系表，不应该在这里删除
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        // 普通用户不能访问该接口
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            return HttpResult.fail("无访问权限");
        }
        if (id != null) {
            int del = juryDeptUserService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }*/

}

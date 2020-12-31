package com.lhsz.bandian.jt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.response.JuryDeptDTO;
import com.lhsz.bandian.jt.DTO.response.JuryUserDTO;
import com.lhsz.bandian.jt.entity.JuryDeptUser;
import com.lhsz.bandian.jt.service.IJuryDeptService;
import com.lhsz.bandian.jt.service.IJuryDeptUserService;
import com.lhsz.bandian.jt.service.IJuryUserService;
import com.lhsz.bandian.sys.DTO.query.QueryUserDTO;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IUserRoleService;
import com.lhsz.bandian.sys.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lizhiguo
 * 2020/7/31 10:19
 */
@RestController
@RequestMapping("/jt/jury-user")
public class JuryUserController extends BaseController {
    @Autowired
    private IJuryUserService juryUserService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IJuryDeptUserService juryDeptUserService;
    @Autowired
    private IUserRoleService userRoleService;

    @PostMapping()
    public HttpResult add(@RequestBody JuryUserDTO userDTO){
        User user=userService.getUserByName(userDTO.getUserName());
        if(user!=null){
            logb.info("用户添加失败，用户名已存在");
            return HttpResult.fail("用户名已存在！");
        }
        juryUserService.addUserAndJury(userDTO);
        logb.info("添加用户成功！");
        return HttpResult.ok();
    }
    @PutMapping()
    public HttpResult update(@RequestBody JuryUserDTO userDTO){
        juryUserService.updateUserAndJury(userDTO);
        return HttpResult.ok();
    }

    /**
     * 用户列表
     * @param user
     * @return
     */
    @GetMapping()
    public HttpResult listUser(QueryUserDTO user){
        startPage();
        List<JuryUserDTO> listuser=juryUserService.listQueryUserDTO(user);
        return HttpResult.ok(getDataTable(listuser));
    }

    /**
     * 用户列表
     * @param user
     * @return
     */
    @GetMapping("deptUser")
    public HttpResult deptIdUser(QueryUserDTO user){
//        startPage();
//        List<JuryDeptUser> juryDeptUser = juryDeptUserService.selectById(deptId);
//        List<User> list1 = new ArrayList();
//        for (JuryDeptUser list : juryDeptUser){
//            User user = userService.selectDeptIdUser(list.getUserId());
//            list1.add(user);
//        }
//        return HttpResult.ok(list1);
        startPage();
        List<JuryUserDTO> listuser=juryUserService.listQueryUserDTO(user);
        return HttpResult.ok(getDataTable(listuser));

    }

    /**
     * 用户详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult showUser(@PathVariable("id") String id){
        JuryUserDTO user=juryUserService.getUserDTO(id);
        return HttpResult.ok(user);
    }

    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult deleteUser(@PathVariable("id") String id){
        if (id != null) {
            juryUserService.delUserAndJury(id);
            return HttpResult.ok();
        }
        return HttpResult.error();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除")
    @ApiImplicitParam(name = "ids", value = "ID集合，多个ID逗号隔开", dataType = "String")
    @PostMapping("/delete")
    public HttpResult deleteBatch (@RequestBody() String ids) {
        ids = ids.replace("\"", "");
        if(juryUserService.removeByIds(Arrays.asList(ids.split(",")))){
            String[] id = ids.split(",");
            for(String userId : id) {
                QueryWrapper qw = new QueryWrapper();
                qw.eq("user_id", userId);
                juryDeptUserService.remove(qw);
                userRoleService.remove(qw);
            }
            return HttpResult.ok();
        }
        else {
            return HttpResult.fail();
        }
    }

}

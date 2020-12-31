package com.lhsz.bandian.jt.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.response.JuryDeclarePlanDTO;
import com.lhsz.bandian.jt.entity.JuryDeclarePlan;
import com.lhsz.bandian.jt.entity.JuryDeptUser;
import com.lhsz.bandian.jt.service.IJuryDeclarePlanService;
import com.lhsz.bandian.jt.service.IJuryDeptUserService;
import com.lhsz.bandian.sys.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 评委会申报计划 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-08-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/jt/juryDeclarePlan")
public class JuryDeclarePlanController extends BaseController {
    private final IJuryDeclarePlanService juryDeclarePlanService;
    private final IJuryDeptUserService iJuryDeptUserService;

    @Autowired
    public JuryDeclarePlanController(IJuryDeclarePlanService juryDeclarePlanService, IJuryDeptUserService iJuryDeptUserService) {
        this.juryDeclarePlanService = juryDeclarePlanService;
        this.iJuryDeptUserService = iJuryDeptUserService;
    }


    /**
     * 查询评委会申报计划
     */
    @GetMapping()
    public HttpResult page(JuryDeclarePlan juryDeclarePlan) {
        try {
            startPage();
            return HttpResult.ok(getDataTable(juryDeclarePlanService.list(juryDeclarePlan)));
        } catch (Exception e) {
            log.debug("查询评委会申报计划异常",e);
            return HttpResult.error("系统内部异常");
        }
    }
    /**
     * 查询评委会申报计划
     */
    @GetMapping("list")
    public HttpResult list(JuryDeclarePlan juryDeclarePlan) {
        try {
            return HttpResult.ok(juryDeclarePlanService.list(juryDeclarePlan));
        } catch (Exception e) {
            log.debug("查询评委会申报计划异常",e);
            return HttpResult.error("系统内部异常");
        }
    }


    /**
     * 添加评委会申报计划
     */
    @PostMapping()
    public HttpResult add(@RequestBody JuryDeclarePlan juryDeclarePlan){
        // 普通用户不能访问该接口
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() != 3) {
            return HttpResult.fail("非评委会账户无法操作");
        }
        try {
            JuryDeptUser juryDeptUser = iJuryDeptUserService.selectByUserId(user.getUserId());
            juryDeclarePlan.setDeptId(juryDeptUser.getDeptId());
            juryDeclarePlanService.add(juryDeclarePlan);
            return HttpResult.ok();
        }catch (NullPointerException nullPointerException){
            log.debug("添加评委会申报计划异常",nullPointerException);
            return HttpResult.error("当前用户不属于任何评委会");
        } catch (Exception e) {
            log.debug("添加评委会申报计划异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改申报流程
     */
    @PutMapping()
    public HttpResult update(@RequestBody JuryDeclarePlan juryDeclarePlan) {
        // 普通用户不能访问该接口
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            return HttpResult.fail("无访问权限");
        }
        try {
            juryDeclarePlanService.update(juryDeclarePlan);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改评委会申报计划异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 评委会申报计划详情
     *
     * @param id 申报流程id
     * @return 申报流程
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        try {
            JuryDeclarePlanDTO juryDeclarePlanDTO = juryDeclarePlanService.selectById(id);
            return HttpResult.ok(juryDeclarePlanDTO);
        } catch (Exception e) {
            log.debug("获取评委会申报计划详情异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 根据ID删除评委会申报计划
     *
     * @param id 申报流程id
     * @return 删除数量
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        // 普通用户不能访问该接口
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            return HttpResult.fail("无访问权限");
        }
        if (id != null) {
            int del = juryDeclarePlanService.del(id);
            return HttpResult.ok(del);
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
        if(juryDeclarePlanService.removeByIds(Arrays.asList(ids.split(",")))){
            return HttpResult.ok();
        }
        else {
            return HttpResult.fail();
        }
    }

}

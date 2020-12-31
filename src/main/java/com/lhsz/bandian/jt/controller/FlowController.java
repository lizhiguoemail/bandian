package com.lhsz.bandian.jt.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.response.FlowDTO;
import com.lhsz.bandian.jt.entity.Flow;
import com.lhsz.bandian.jt.service.IFlowService;
import com.lhsz.bandian.sys.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 申报流程 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/jt/flow")
public class FlowController extends BaseController {

    private final IFlowService flowService;

    @Autowired
    public FlowController(IFlowService flowService) {
        this.flowService = flowService;
    }

    /**
     * 查询申报流程
     */
    @GetMapping("/all")
    public HttpResult listAll() {
       List<SelectDTO> result = flowService.listAll();
        return HttpResult.ok(result);
    }
    /**
     * 查询申报流程
     */
    @GetMapping()
    public HttpResult list(Flow flow) {
        try {
            startPage();
            return HttpResult.ok(getDataTable(flowService.list(flow)));
        } catch (Exception e) {
            log.debug("查询申报流程异常",e);
            return HttpResult.error("系统内部异常");
        }
    }


    /**
     * 添加申报流程
     */
    @PostMapping()
    public HttpResult add(@RequestBody Flow flow){
        // 普通用户不能访问该接口
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            return HttpResult.fail("无访问权限");
        }
        try {
            flowService.add(flow);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加申报流程异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改申报流程
     */
    @PutMapping()
    public HttpResult update(@RequestBody Flow flow) {
        // 普通用户不能访问该接口
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            return HttpResult.fail("无访问权限");
        }
        try {
            flowService.update(flow);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改申报流程异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 申报流程详情
     *
     * @param id 申报流程id
     * @return 申报流程
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        try {
            FlowDTO flowDTO = flowService.selectById(id);
            return HttpResult.ok(flowDTO);
        } catch (Exception e) {
            log.debug("获取申报流程详情异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 根据ID删除申报流程
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
            int del = flowService.del(id);
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
        if(flowService.removeByIds(Arrays.asList(ids.split(",")))){
            return HttpResult.ok();
        }
        else {
            return HttpResult.fail();
        }
    }

}

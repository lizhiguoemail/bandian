package com.lhsz.bandian.jt.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.jt.DTO.response.JuryDeclareSubjectDTO;
import com.lhsz.bandian.jt.entity.JuryDeclareSubject;
import com.lhsz.bandian.jt.service.IJuryDeclareSubjectService;
import com.lhsz.bandian.sys.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.Arrays;

/**
 * <p>
 * 评委会申报对象资格 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/jury-declare-subject")
public class JuryDeclareSubjectController extends BaseController {

    private final IJuryDeclareSubjectService juryDeclareSubjectService;

    @Autowired
    public JuryDeclareSubjectController(IJuryDeclareSubjectService juryDeclareSubjectService) {
        this.juryDeclareSubjectService = juryDeclareSubjectService;
    }

    /**
     * 查询评委会申报对象资格
     */
    @GetMapping()
    public HttpResult list(JuryDeclareSubject juryDeclareSubject) {
        try {
            startPage();
            return HttpResult.ok(getDataTable(juryDeclareSubjectService.list(juryDeclareSubject)));
        } catch (Exception e) {
            log.debug("查询评委会申报对象资格异常",e);
            return HttpResult.error("系统内部异常");
        }
    }


    /**
     * 添加评委会申报对象资格
     */
    @PostMapping()
    public HttpResult add(@RequestBody JuryDeclareSubject juryDeclareSubject){
        try {
            // 普通用户不能访问该接口
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                return HttpResult.fail("无访问权限");
            }
            juryDeclareSubjectService.add(juryDeclareSubject);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加评委会申报对象资格异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改评委会申报对象资格
     */
    @PutMapping()
    public HttpResult update(@RequestBody JuryDeclareSubject juryDeclareSubject) {
        try {
            // 普通用户不能访问该接口
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                return HttpResult.fail("无访问权限");
            }
            juryDeclareSubjectService.update(juryDeclareSubject);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改评委会申报对象资格异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 评委会申报对象资格详情
     *
     * @param id 资格详情id
     * @return 资格详情
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        try {
            JuryDeclareSubjectDTO juryDeclareSubjectDTO = juryDeclareSubjectService.selectById(id);
            return HttpResult.ok(juryDeclareSubjectDTO);
        } catch (Exception e) {
            log.debug("查询评委会申报对象资格详情异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 根据ID删除评委会申报对象资格
     *
     * @param id 资格详情id
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
            int del = juryDeclareSubjectService.del(id);
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
        if(juryDeclareSubjectService.removeByIds(Arrays.asList(ids.split(",")))){
            return HttpResult.ok();
        }
        else {
            return HttpResult.fail();
        }
    }
}

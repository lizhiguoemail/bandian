package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.jt.DTO.request.ChildTeachWorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachWorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachWorkUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtChildTeachWorkDTO;
import com.lhsz.bandian.jt.entity.ChildTeachWork;
import com.lhsz.bandian.jt.service.IChildTeachWorkService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.List;

/**
 * <p>
 * 中小学幼儿老师日常教学 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/jt/child-teach-work")
public class ChildTeachWorkController extends BaseController {

    @Autowired
    private IChildTeachWorkService iChildTeachWorkService;


    /**
     * 查询中小学幼儿老师日常教学
     *
     * @param childTeachWork
     * @return
     */
    @GetMapping()
    public HttpResult ListChildTeachWork(ChildTeachWork childTeachWork) {
        startPage();
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            childTeachWork.setUserId(user.getUserId());

        }
        List<JtChildTeachWorkDTO> jtChildTeachWorkDTOS = iChildTeachWorkService.listChildTeachWork(childTeachWork);
        return HttpResult.ok(getDataTable(jtChildTeachWorkDTOS));
    }

    /**
     * 根据id进行查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult getChildTeachWork(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            ChildTeachWorkDetailDTO childTeachWork = iChildTeachWorkService.detailById(id);
            if (!(childTeachWork.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(childTeachWork);
        }
        ChildTeachWorkDetailDTO childTeachWork = iChildTeachWorkService.detailById(id);
        if (childTeachWork != null) {
            return HttpResult.ok(childTeachWork);
        }
        return HttpResult.fail();
    }

    /**
     * 添加中小学幼儿老师日常教学
     *
     * @param jtChildTeachWorkDTO
     * @return
     */
    @PostMapping()
    public HttpResult addChildTeachWork(@RequestBody ChildTeachWorkAddDTO jtChildTeachWorkDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            jtChildTeachWorkDTO.getData().setUserId(user.getUserId());
        } else {
            if(jtChildTeachWorkDTO.getData().getUserId() == null) {
                return HttpResult.fail("用户信息不能为空");
            }
        }
        jtChildTeachWorkDTO.getData().setChkStatus("00");
        jtChildTeachWorkDTO.getData().setSchoolYear(jtChildTeachWorkDTO.getData().getSchoolYear().substring(0, 4));
        jtChildTeachWorkDTO.getData().setIsDeleted(0);
        iChildTeachWorkService.addChildTeachWork(jtChildTeachWorkDTO);
        return HttpResult.ok();
    }

    /**
     * 更新中小学幼儿老师日常教学
     *
     * @param jtChildTeachWorkDTO
     * @return
     */
    @PutMapping()
    public HttpResult updateChildTeachWork(@RequestBody ChildTeachWorkUpdateDTO jtChildTeachWorkDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            if ("03".equals(jtChildTeachWorkDTO.getData().getChkStatus()) || "04".equals(jtChildTeachWorkDTO.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");
            }
            if (!(jtChildTeachWorkDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
        }
        jtChildTeachWorkDTO.getData().setSchoolYear(jtChildTeachWorkDTO.getData().getSchoolYear().substring(0, 4));
        iChildTeachWorkService.updateChildTeachWork(jtChildTeachWorkDTO);
        return HttpResult.ok();
    }

    /**
     * 删除中小学幼儿老师日常教学
     *
     * @param Id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delChildTeachWork(@PathVariable("id") String Id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            ChildTeachWorkDetailDTO childTeachWork = iChildTeachWorkService.detailById(Id);
            if ("03".equals(childTeachWork.getData().getChkStatus()) || "04".equals(childTeachWork.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");
            }
            if (!(childTeachWork.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
        }
        int delChildTeachWork = iChildTeachWorkService.delChildTeachWork(Id);
        if (delChildTeachWork != 1) {
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }
}

package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.jt.DTO.request.AwardsDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachOpenAddDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachOpenDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachOpenUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtChildTeachOpenDTO;
import com.lhsz.bandian.jt.entity.ChildTeachOpen;
import com.lhsz.bandian.jt.service.IChildTeachOpenService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.List;

/**
 * <p>
 * 中小学幼儿老师公开课 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/jt/child-teach-open")
public class ChildTeachOpenController extends BaseController {

    @Autowired
    private IChildTeachOpenService iChildTeachOpenService;


    /**
     * 查询中小学幼儿老师公开课
     *
     * @param childTeachOpen
     * @return
     */
    @GetMapping()
    public HttpResult ListChildTeachOpen(ChildTeachOpen childTeachOpen) {
        startPage();
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            childTeachOpen.setUserId(user.getUserId());
        }
        List<JtChildTeachOpenDTO> jtChildTeachOpenDTOS = iChildTeachOpenService.listChildTeachOpen(childTeachOpen);
        return HttpResult.ok(getDataTable(jtChildTeachOpenDTOS));
    }

    /**
     * 根据id进行查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult getChildTeachOpen(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            ChildTeachOpenDetailDTO childTeachOpen = iChildTeachOpenService.detailById(id);
            if (!(childTeachOpen.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(childTeachOpen);
        }
        ChildTeachOpenDetailDTO childTeachOpen = iChildTeachOpenService.detailById(id);
        if (childTeachOpen != null) {
            return HttpResult.ok(childTeachOpen);
        }
        return HttpResult.fail();
    }

    /**
     * 添加中小学幼儿老师公开课
     *
     * @param jtChildTeachOpenDTO
     * @return
     */
    @PostMapping()
    public HttpResult addChildTeachOpen(@RequestBody ChildTeachOpenAddDTO jtChildTeachOpenDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            jtChildTeachOpenDTO.getData().setUserId(user.getUserId());
        } else {
            if(jtChildTeachOpenDTO.getData().getUserId() == null) {
                return HttpResult.fail("用户信息不能为空");
            }
        }
        jtChildTeachOpenDTO.getData().setChkStatus("00");
        jtChildTeachOpenDTO.getData().setCourseDate(jtChildTeachOpenDTO.getData().getCourseDate().substring(0, 10));
        jtChildTeachOpenDTO.getData().setIsDeleted(0);
        iChildTeachOpenService.addChildTeachOpen(jtChildTeachOpenDTO);
        return HttpResult.ok();
    }

    /**
     * 更新中小学幼儿老师公开课
     *
     * @param jtChildTeachOpenDTO
     * @return
     */
    @PutMapping()
    public HttpResult updateChildTeachOpen(@RequestBody ChildTeachOpenUpdateDTO jtChildTeachOpenDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            if ("03".equals(jtChildTeachOpenDTO.getData().getChkStatus()) || "04".equals(jtChildTeachOpenDTO.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");
            }
            if (!(jtChildTeachOpenDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
        }
        jtChildTeachOpenDTO.getData().setCourseDate(jtChildTeachOpenDTO.getData().getCourseDate().substring(0, 10));
        iChildTeachOpenService.updateChildTeachOpen(jtChildTeachOpenDTO);
        return HttpResult.ok();
    }

    /**
     * 删除中小学幼儿老师公开课
     *
     * @param Id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delChildTeachOpen(@PathVariable("id") String Id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            ChildTeachOpenDetailDTO childTeachOpen = iChildTeachOpenService.detailById(Id);
            if ("03".equals(childTeachOpen.getData().getChkStatus()) || "04".equals(childTeachOpen.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");
            }
            if (!(childTeachOpen.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
        }
        int delChildTeachOpen = iChildTeachOpenService.delChildTeachOpen(Id);
        if (delChildTeachOpen != 1) {
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }
}

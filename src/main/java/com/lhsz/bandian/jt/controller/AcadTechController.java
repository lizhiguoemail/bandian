package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.jt.DTO.request.AcadTechAddDTO;
import com.lhsz.bandian.jt.DTO.request.AcadTechDetailDTO;
import com.lhsz.bandian.jt.DTO.request.AcadTechUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtAcadTechDTO;
import com.lhsz.bandian.jt.entity.AcadTech;
import com.lhsz.bandian.jt.service.IAcadTechService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.List;

/**
 * <p>
 * 学术技术兼职 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/jt/acad-tech")
public class AcadTechController extends BaseController {

    @Autowired
    private IAcadTechService acadTechService;

    /**
     * 查询学术技术兼职
     *
     * @param acadTech
     * @return
     */
    @GetMapping()
    public HttpResult listAcadTech(AcadTech acadTech) {
        startPage();
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            acadTech.setUserId(user.getUserId());

        }
        List<JtAcadTechDTO> jtAcadTechDTOS = acadTechService.listAcadTech(acadTech);
        return HttpResult.ok(getDataTable(jtAcadTechDTOS));

    }

    /**
     * 根据用户ID进行查询
     *
     * @param techId
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult getAcadTech(@PathVariable("id") String techId) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            AcadTechDetailDTO detailDTO = acadTechService.detailById(techId);
            if (!(detailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(detailDTO);
        }
        AcadTechDetailDTO detailDTO = acadTechService.detailById(techId);
        if (detailDTO != null) {
            return HttpResult.ok(detailDTO);
        }
        return HttpResult.fail();
    }

    /**
     * 添加学术技术兼职
     *
     * @param jtAcadTechDTO
     * @return
     */
    @PostMapping()
    public HttpResult addAcadTech(@RequestBody AcadTechAddDTO jtAcadTechDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            jtAcadTechDTO.getData().setUserId(user.getUserId());
        } else {
            if(jtAcadTechDTO.getData().getUserId() == null) {
                return HttpResult.fail("用户信息不能为空");
            }
        }
        jtAcadTechDTO.getData().setChkStatus("00");
        acadTechService.addAcadTechDTO(jtAcadTechDTO);
        return HttpResult.ok();
    }

    /**
     * 更新学术技术兼职
     *
     * @param jtAcadTechDTO
     * @return
     */
    @PutMapping()
    public HttpResult updateAcadTech(@RequestBody AcadTechUpdateDTO jtAcadTechDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            if ("03".equals(jtAcadTechDTO.getData().getChkStatus()) || "04".equals(jtAcadTechDTO.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");
            }
            if (!(jtAcadTechDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
        }
        jtAcadTechDTO.getData().setBeginDate(jtAcadTechDTO.getData().getBeginDate().substring(0, 10));
        jtAcadTechDTO.getData().setEndDate(jtAcadTechDTO.getData().getEndDate().substring(0, 10));
        acadTechService.updateAcadTechDTO(jtAcadTechDTO);
        return HttpResult.ok();
    }

    /**
     * 删除学术技术兼职
     *
     * @param techId
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delAcadTech(@PathVariable("id") String techId) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            AcadTechDetailDTO detailDTO = acadTechService.detailById(techId);
            if ("03".equals(detailDTO.getData().getChkStatus()) || "04".equals(detailDTO.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");
            }
            if (!(detailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
        }
        int acadTech = acadTechService.delectAcadTech(techId);
        if (acadTech != 1) {
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }
}

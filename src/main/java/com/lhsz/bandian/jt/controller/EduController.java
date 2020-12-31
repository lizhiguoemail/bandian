package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.jt.DTO.request.EduAddDTO;
import com.lhsz.bandian.jt.DTO.request.EduDetailDTO;
import com.lhsz.bandian.jt.DTO.request.EduUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtEduDTO;
import com.lhsz.bandian.jt.entity.Edu;
import com.lhsz.bandian.jt.service.IEduService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.List;

/**
 * <p>
 * 教育经历 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/jt/edu")
public class EduController extends BaseController {

    @Autowired
    private IEduService iEduService;

    /**
     * 查询教育经历
     *
     * @param edu
     * @return
     */
    @GetMapping()
    public HttpResult ListEdu(Edu edu) {
        startPage();
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            edu.setUserId(user.getUserId());

        }
        List<JtEduDTO> jtEduDTOS = iEduService.listEdu(edu);
        return HttpResult.ok(getDataTable(jtEduDTOS));
    }

    /**
     * 根据id进行查询
     *
     * @param Id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult getEdu(@PathVariable("id") String Id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            EduDetailDTO edu = iEduService.detailById(Id);
            if (!(edu.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(edu);
        }
        EduDetailDTO edu = iEduService.detailById(Id);
        if (edu != null) {
            return HttpResult.ok(edu);
        }
        return HttpResult.fail();
    }

    /**
     * 添加教育经历
     *
     * @param jtEduDTO
     * @return
     */
    @PostMapping()
    public HttpResult addEdu(@RequestBody EduAddDTO jtEduDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            jtEduDTO.getData().setUserId(user.getUserId());
        } else {
            if(jtEduDTO.getData().getUserId() == null) {
                return HttpResult.fail("用户信息不能为空");
            }
        }
        jtEduDTO.getData().setChkStatus("00");
        jtEduDTO.getData().setBeginDate(jtEduDTO.getData().getBeginDate().substring(0, 10));
        jtEduDTO.getData().setEndDate(jtEduDTO.getData().getEndDate().substring(0, 10));
        jtEduDTO.getData().setGradTime(jtEduDTO.getData().getGradTime().substring(0, 10));
        jtEduDTO.getData().setIsDeleted(0);
        iEduService.addEdu(jtEduDTO);
        return HttpResult.ok();
    }

    /**
     * 更新教育经历
     *
     * @param jtEduDTO
     * @return
     */
    @PutMapping()
    public HttpResult updateEdu(@RequestBody EduUpdateDTO jtEduDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            if ("03".equals(jtEduDTO.getData().getChkStatus()) || "04".equals(jtEduDTO.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");
            }
            if (!(jtEduDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人修改信息");
            }
        }
        jtEduDTO.getData().setBeginDate(jtEduDTO.getData().getBeginDate().substring(0, 10));
        jtEduDTO.getData().setEndDate(jtEduDTO.getData().getEndDate().substring(0, 10));
        jtEduDTO.getData().setGradTime(jtEduDTO.getData().getGradTime().substring(0, 10));
        iEduService.updateEdu(jtEduDTO);
        return HttpResult.ok();
    }

    /**
     * 删除教育经历
     *
     * @param Id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delEdu(@PathVariable("id") String Id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            EduDetailDTO edu = iEduService.detailById(Id);
            if ("03".equals(edu.getData().getChkStatus()) || "04".equals(edu.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");
            }
            if (!(edu.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人修改信息");
            }
        }
        int delEdu = iEduService.delEdu(Id);
        if (delEdu != 1) {
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }

}

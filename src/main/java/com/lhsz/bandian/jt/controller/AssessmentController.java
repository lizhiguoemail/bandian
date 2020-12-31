package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.jt.DTO.request.AssessmentAddDTO;
import com.lhsz.bandian.jt.DTO.request.AssessmentDetailDTO;
import com.lhsz.bandian.jt.DTO.request.AssessmentUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtAssessmentDTO;
import com.lhsz.bandian.jt.entity.Assessment;
import com.lhsz.bandian.jt.service.IAssessmentService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.List;

/**
 * <p>
 * 任职考核 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/jt/assessment")
public class AssessmentController extends BaseController {

    @Autowired
    private IAssessmentService assessmentService;


    /**
     * 查询任职考核
     *
     * @param assessment
     * @return
     */
    @GetMapping()
    public HttpResult listAssessment(Assessment assessment) {
        startPage();
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            assessment.setUserId(user.getUserId());

        }
        List<JtAssessmentDTO> assessments = assessmentService.listAssessment(assessment);
        return HttpResult.ok(getDataTable(assessments));

    }

    /**
     * 根据id进行查询
     *
     * @param assessmentId
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult getAssessment(@PathVariable("id") String assessmentId) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            AssessmentDetailDTO detailDTO = assessmentService.detailById(assessmentId);
            if (!(detailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(detailDTO);
        }
        AssessmentDetailDTO detailDTO = assessmentService.detailById(assessmentId);
        if (detailDTO != null) {
            return HttpResult.ok(detailDTO);
        }
        return HttpResult.fail();
    }

    /**
     * 添加
     *
     * @param jtAssessmentDTO
     * @return
     */
    @PostMapping()
    public HttpResult addAssessment(@RequestBody AssessmentAddDTO jtAssessmentDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            jtAssessmentDTO.getData().setUserId(user.getUserId());
        } else {
            if(jtAssessmentDTO.getData().getUserId() == null) {
                return HttpResult.fail("用户信息不能为空");
            }
        }
        jtAssessmentDTO.getData().setChkStatus("00");
        jtAssessmentDTO.getData().setAssessmentYear(jtAssessmentDTO.getData().getAssessmentYear().substring(0, 4));
        assessmentService.addAssessment(jtAssessmentDTO);
        return HttpResult.ok();
    }

    /**
     * 更新
     *
     * @param jtAssessmentDTO
     * @return
     */
    @PutMapping()
    public HttpResult updateAssessment(@RequestBody AssessmentUpdateDTO jtAssessmentDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            if ("03".equals(jtAssessmentDTO.getData().getChkStatus()) || "04".equals(jtAssessmentDTO.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");
            }
            if (!(jtAssessmentDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
        }
        jtAssessmentDTO.getData().setAssessmentYear(jtAssessmentDTO.getData().getAssessmentYear().substring(0, 4));
        assessmentService.updateAssessment(jtAssessmentDTO);
        return HttpResult.ok();
    }

    /**
     * 根据id进行删除
     *
     * @param assessmentId
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delAssessment(@PathVariable("id") String assessmentId) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            AssessmentDetailDTO detailDTO = assessmentService.detailById(assessmentId);
            if ("03".equals(detailDTO.getData().getChkStatus()) || "04".equals(detailDTO.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");
            }
            if (!(detailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
        }
        int delAssessment = assessmentService.delAssessment(assessmentId);
        if (delAssessment != 1) {
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }
}

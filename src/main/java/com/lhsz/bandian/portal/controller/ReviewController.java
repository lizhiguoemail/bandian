package com.lhsz.bandian.portal.controller;

import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.response.JuryDeclarePlanDTO;
import com.lhsz.bandian.jt.entity.JuryDeclarePlan;
import com.lhsz.bandian.jt.service.IJuryDeclarePlanService;
import com.lhsz.bandian.jt.service.IUserProfileService;
import com.lhsz.bandian.sys.service.IAttachService;
import com.lhsz.bandian.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lizhiguo
 * 2020/7/30 14:35
 */
@Slf4j
@RestController
@RequestMapping("/portal/jt/juryDeclarePlan")
public class ReviewController extends BaseController {
    /**
     * 评委会申报计划
     */
    @Autowired
    private IJuryDeclarePlanService juryDeclarePlanService;

//    /**
//     * 初始化
//     * @param userService 用户服务
//     * @param userProfileService 用户资料服务
//     * @param attachService  附件服务
//     */
//    @Autowired
//    public ReviewController(IUserService userService, IUserProfileService userProfileService, IAttachService attachService) {
//        this.userService = userService;
//        this.userProfileService = userProfileService;
//        this.attachService = attachService;
//    }

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
     * 根据declareCategory搜索评委会申报计划
     *
     * @param
     * @return
     */
    @GetMapping("/search")
    public HttpResult search(@PathVariable("declareCategory") String declareCategory,@PathVariable("declareSpecialty") String declareSpecialty,@PathVariable("declareTitle") String declareTitle) {
        try {
            List<JuryDeclarePlanDTO> juryDeclarePlanDTO = juryDeclarePlanService.search(declareCategory,declareSpecialty,declareTitle);
            return HttpResult.ok(juryDeclarePlanDTO);
        } catch (Exception e) {
            log.debug("获取评委会申报计划异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 评委会申报计划详情
     * @param id 申报流程id
     * @return 申报流程
     */
    @GetMapping("/{id}")
    public HttpResult getReviewById(@PathVariable("id") String id) {
        try {
            JuryDeclarePlanDTO juryDeclarePlanDTO = juryDeclarePlanService.selectById(id);
            return HttpResult.ok(juryDeclarePlanDTO);
        } catch (Exception e) {
            log.debug("获取评委会申报计划详情异常",e);
            return HttpResult.error("系统内部异常");
        }
    }
}

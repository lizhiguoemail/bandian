package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.PartyTeachTeamWorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachTeamWorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachTeamWorkUpdateDTO;
import com.lhsz.bandian.jt.entity.PartyTeachTeamWork;
import com.lhsz.bandian.jt.service.IPartyTeachTeamWorkService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 党校教师团队工作 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/party-teach-team-work")
public class PartyTeachTeamWorkController extends BaseController {
    
    private final IPartyTeachTeamWorkService partyTeachTeamWorkService;

    private final TokenService tokenService;

    @Autowired
    public PartyTeachTeamWorkController(IPartyTeachTeamWorkService partyTeachTeamWorkService, TokenService tokenService) {
        this.partyTeachTeamWorkService = partyTeachTeamWorkService;
        this.tokenService = tokenService;
    }

    /**
     * 查询党校教师团队工作
     */
    @GetMapping()
    public HttpResult list(PartyTeachTeamWork partyTeachTeamWork) {
        try {
            // 会员只能查询本userId的记录
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                partyTeachTeamWork.setUserId(user.getUserId());
            }
            startPage();
            return HttpResult.ok(getDataTable(partyTeachTeamWorkService.list(partyTeachTeamWork)));
        } catch (Exception e) {
            log.debug("查询党校教师团队工作异常",e);
            return HttpResult.error("系统内部异常");
        }
    }


    /**
     * 添加党校教师团队工作
     */
    @PostMapping()
    public HttpResult add(@RequestBody PartyTeachTeamWorkAddDTO partyTeachTeamWork){
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if (user.getUserType() == 2) {
                partyTeachTeamWork.getData().setUserId(user.getUserId());
            } else {
                if(partyTeachTeamWork.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            partyTeachTeamWork.getData().setChkStatus("00");
            partyTeachTeamWorkService.add(partyTeachTeamWork);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加党校教师团队工作异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改党校教师团队工作
     */
    @PutMapping()
    public HttpResult update(@RequestBody PartyTeachTeamWorkUpdateDTO partyTeachTeamWork) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人修改信息
            if(user.getUserType() == 2 && !(user.getUserId().equals(partyTeachTeamWork.getData().getUserId()))){
                return HttpResult.fail("不允许为他人修改信息");
            }
            partyTeachTeamWorkService.update(partyTeachTeamWork);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改党校教师团队工作异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 党校教师团队工作详情
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        try {
            // 阻止普通用户查询他人信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                PartyTeachTeamWorkDetailDTO partyTeachTeamWorkDetailDTO = partyTeachTeamWorkService.detailById(id);
                if("03".equals(partyTeachTeamWorkDetailDTO.getData().getChkStatus()) || "04".equals(partyTeachTeamWorkDetailDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许修改");
                }
                if(!(user.getUserId().equals(partyTeachTeamWorkDetailDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许普通用户查询他人信息");
                }
                return HttpResult.ok(partyTeachTeamWorkDetailDTO);
            }
            PartyTeachTeamWorkDetailDTO partyTeachTeamWorkDetailDTO = partyTeachTeamWorkService.detailById(id);
            return HttpResult.ok(partyTeachTeamWorkDetailDTO);
        } catch (Exception e) {
            log.debug("获取党校教师团队工作详情异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 根据ID删除党校教师团队工作
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            // 阻止普通用户为他人删除信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                PartyTeachTeamWorkDetailDTO partyTeachTeamWorkDetailDTO = partyTeachTeamWorkService.detailById(id);
                if("03".equals(partyTeachTeamWorkDetailDTO.getData().getChkStatus()) || "04".equals(partyTeachTeamWorkDetailDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许删除");
                }
                if(!(user.getUserId().equals(partyTeachTeamWorkDetailDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人删除信息");
                }
            }
            int del = partyTeachTeamWorkService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }

}

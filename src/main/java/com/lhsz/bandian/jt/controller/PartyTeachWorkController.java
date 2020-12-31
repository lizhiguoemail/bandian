package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.PartyTeachWorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachWorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachWorkUpdateDTO;
import com.lhsz.bandian.jt.entity.PartyTeachWork;
import com.lhsz.bandian.jt.service.IPartyTeachWorkService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 党校教学工作 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/party-teach-work")
public class PartyTeachWorkController extends BaseController {
    
    private final IPartyTeachWorkService partyTeachWorkService;
    private final TokenService tokenService;

    @Autowired
    public PartyTeachWorkController(IPartyTeachWorkService partyTeachWorkService, TokenService tokenService) {
        this.partyTeachWorkService = partyTeachWorkService;
        this.tokenService = tokenService;
    }

    /**
     * 查询党校教学工作
     */
    @GetMapping()
    public HttpResult list(PartyTeachWork partyTeachWork) {
        try {
            // 会员只能查询本userId的记录
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                partyTeachWork.setUserId(user.getUserId());
            }
            startPage();
            return HttpResult.ok(getDataTable(partyTeachWorkService.list(partyTeachWork)));
        } catch (Exception e) {
            log.debug("查询党校教学工作异常",e);
            return HttpResult.error("系统内部异常");
        }
    }


    /**
     * 添加党校教学工作
     */
    @PostMapping()
    public HttpResult add(@RequestBody PartyTeachWorkAddDTO partyTeachWork){
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if (user.getUserType() == 2) {
                partyTeachWork.getData().setUserId(user.getUserId());
            } else {
                if(partyTeachWork.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            partyTeachWork.getData().setChkStatus("00");
            partyTeachWorkService.add(partyTeachWork);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加党校教学工作异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改党校教学工作
     */
    @PutMapping()
    public HttpResult update(@RequestBody PartyTeachWorkUpdateDTO partyTeachWork) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人修改信息
            if(user.getUserType() == 2){
                if("03".equals(partyTeachWork.getData().getChkStatus()) || "04".equals(partyTeachWork.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许修改");
                }
                if(!(user.getUserId().equals(partyTeachWork.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            partyTeachWorkService.update(partyTeachWork);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改党校教学工作异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 党校教学工作详情
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        try {
            // 阻止普通用户查询他人信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                PartyTeachWorkDetailDTO partyTeachWorkDetailDTO = partyTeachWorkService.detailById(id);
                if(!(user.getUserId().equals(partyTeachWorkDetailDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许普通用户查询他人信息");
                }
                return HttpResult.ok(partyTeachWorkDetailDTO);
            }
            PartyTeachWorkDetailDTO partyTeachWorkDetailDTO = partyTeachWorkService.detailById(id);
            return HttpResult.ok(partyTeachWorkDetailDTO);
        } catch (Exception e) {
            log.debug("获取党校教学工作详情异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 根据ID删除党校教学工作
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            // 阻止普通用户为他人删除信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                PartyTeachWorkDetailDTO partyTeachWorkDetailDTO = partyTeachWorkService.detailById(id);
                if("03".equals(partyTeachWorkDetailDTO.getData().getChkStatus()) || "04".equals(partyTeachWorkDetailDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许删除");
                }
                if(!(user.getUserId().equals(partyTeachWorkDetailDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人删除信息");
                }
            }
            int del = partyTeachWorkService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

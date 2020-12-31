package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.IncentiveAddDTO;
import com.lhsz.bandian.jt.DTO.request.IncentiveDetailDTO;
import com.lhsz.bandian.jt.DTO.request.IncentiveUpdateDTO;
import com.lhsz.bandian.jt.entity.Incentive;
import com.lhsz.bandian.jt.service.IIncentiveService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 奖惩情况 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/incentive")
public class IncentiveController extends BaseController {

    private final IIncentiveService iIncentiveService;
    private final TokenService tokenService;

    @Autowired
    public IncentiveController(IIncentiveService iIncentiveService, TokenService tokenService) {
        this.iIncentiveService = iIncentiveService;
        this.tokenService = tokenService;
    }


    /**
     * 查询奖惩情况
     */
    @GetMapping()
    public HttpResult list(Incentive incentive) {
        try {
            // 会员只能查询本userId的记录
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                incentive.setUserId(user.getUserId());
            }
            startPage();
            return HttpResult.ok(getDataTable(iIncentiveService.list(incentive)));
        } catch (Exception e) {
            log.debug("查询奖惩情况异常",e);
            return HttpResult.error("系统内部异常");
        }
    }


    /**
     * 添加奖惩情况
     */
    @PostMapping()
    public HttpResult add(@RequestBody IncentiveAddDTO incentive){
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if (user.getUserType() == 2) {
                incentive.getData().setUserId(user.getUserId());
            } else {
                if(incentive.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            incentive.getData().setChkStatus("00");
            iIncentiveService.add(incentive.getData());
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加奖惩情况异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改奖惩情况
     */
    @PutMapping()
    public HttpResult update(@RequestBody IncentiveUpdateDTO incentive) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人修改信息
            if(user.getUserType() == 2){
                if("03".equals(incentive.getData().getChkStatus()) || "04".equals(incentive.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许修改");
                }
                if(!(user.getUserId().equals(incentive.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iIncentiveService.update(incentive);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改奖惩情况异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 奖惩情况详情
     *
     * @param id 奖惩情况id
     * @return IncentiveDetailDTO
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        try {
            // 阻止普通用户查询他人信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                IncentiveDetailDTO incentiveDetailDTO = iIncentiveService.detailById(id);
                if(!(user.getUserId().equals(incentiveDetailDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许普通用户查询他人信息");
                }
                return HttpResult.ok(incentiveDetailDTO);
            }
            IncentiveDetailDTO incentiveDetailDTO = iIncentiveService.detailById(id);
            return HttpResult.ok(incentiveDetailDTO);
        } catch (Exception e) {
            log.debug("获得奖惩情况详情异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 根据ID删除奖惩情况
     *
     * @param id 奖惩情况id
     * @return 删除数量
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            // 阻止普通用户为他人删除信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                IncentiveDetailDTO incentiveDetailDTO = iIncentiveService.detailById(id);
                if("03".equals(incentiveDetailDTO.getData().getChkStatus()) || "04".equals(incentiveDetailDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许删除");
                }
                if(!(user.getUserId().equals(incentiveDetailDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人删除信息");
                }
            }
            int del = iIncentiveService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }

}

package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.SportCoachesAddDTO;
import com.lhsz.bandian.jt.DTO.request.SportCoachesDetailDTO;
import com.lhsz.bandian.jt.DTO.request.SportCoachesUpdateDTO;
import com.lhsz.bandian.jt.entity.SportCoaches;
import com.lhsz.bandian.jt.service.ISportCoachesService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 培养输送运动员 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/sport-coaches")
public class SportCoachesController extends BaseController {
    @Autowired
    private ISportCoachesService iSportCoachesService;
    /**
     * 查询培养输送运动员
     */
    @GetMapping()
    public HttpResult list(SportCoaches sportCoaches) {
        // 会员只能查询本userId的记录
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            sportCoaches.setUserId(user.getUserId());
        }
        startPage();
        return HttpResult.ok(getDataTable(iSportCoachesService.list(sportCoaches)) );
    }

    /**
     * 保存培养输送运动员
     */
    @PostMapping()
    public HttpResult add(@RequestBody SportCoachesAddDTO sportCoachesAddDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            if (user.getUserType() == 2) {
                sportCoachesAddDTO.getData().setUserId(user.getUserId());
            } else {
                if(sportCoachesAddDTO.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            sportCoachesAddDTO.getData().setChkStatus("00");
            iSportCoachesService.add(sportCoachesAddDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加培养输送运动员异常",e);
            return HttpResult.error("系统内部异常");
        }

    }

    /**
     * 修改培养输送运动员
     */
    @PutMapping()
    public HttpResult update(@RequestBody SportCoachesUpdateDTO sportCoachesUpdateDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if(user.getUserType() == 2){
                if("03".equals(sportCoachesUpdateDTO.getData().getChkStatus()) || "04".equals(sportCoachesUpdateDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许添加");
                }
                if(!(user.getUserId().equals(sportCoachesUpdateDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iSportCoachesService.update(sportCoachesUpdateDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改培养输送运动员异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 培养输送运动员详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            SportCoachesDetailDTO sportCoachesDetailDTO = iSportCoachesService.detailById(id);
            if (!(sportCoachesDetailDTO.getData().getUserId().equals(user.getUserId()))){
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(sportCoachesDetailDTO);
        }
        SportCoachesDetailDTO sportCoachesDetailDTO = iSportCoachesService.detailById(id);
        return HttpResult.ok(sportCoachesDetailDTO);
    }

    /**
     * 根据ID删除培养输送运动员
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            SportCoachesDetailDTO sportCoachesDetailDTO = iSportCoachesService.detailById(id);
            if ("03".equals(sportCoachesDetailDTO.getData().getChkStatus()) || "04".equals(sportCoachesDetailDTO.getData().getChkStatus())){
                return HttpResult.fail("当前审核状态不允许删除");
            }
            if (!(sportCoachesDetailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人删除信息");
            }
        }
        if (id != null) {
            int del = iSportCoachesService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

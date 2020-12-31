package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.SportGameAddDTO;
import com.lhsz.bandian.jt.DTO.request.SportGameDetailDTO;
import com.lhsz.bandian.jt.DTO.request.SportGameUpdateDTO;
import com.lhsz.bandian.jt.entity.SportGame;
import com.lhsz.bandian.jt.service.ISportGameService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 竞技体育比赛成果 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/sport-game")
public class SportGameController extends BaseController {
    @Autowired
    private ISportGameService iSportGameService;
    /**
     * 查询竞技体育比赛成果
     */
    @GetMapping()
    public HttpResult list(SportGame sportGame) {
        // 会员只能查询本userId的记录
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            sportGame.setUserId(user.getUserId());
        }
        startPage();
        return HttpResult.ok(getDataTable( iSportGameService.list(sportGame)));
    }

    /**
     * 保存竞技体育比赛成果
     */
    @PostMapping()
    public HttpResult add(@RequestBody SportGameAddDTO sportGameAddDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            if (user.getUserType() == 2) {
                sportGameAddDTO.getData().setUserId(user.getUserId());
            } else {
                if(sportGameAddDTO.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            sportGameAddDTO.getData().setChkStatus("00");
            iSportGameService.add(sportGameAddDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加竞技体育比赛成果异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改竞技体育比赛成果
     */
    @PutMapping()
    public HttpResult update(@RequestBody SportGameUpdateDTO sportGameUpdateDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if(user.getUserType() == 2){
                if("03".equals(sportGameUpdateDTO.getData().getChkStatus()) || "04".equals(sportGameUpdateDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许添加");
                }
                if(!(user.getUserId().equals(sportGameUpdateDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iSportGameService.update(sportGameUpdateDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改竞技体育比赛成果异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 竞技体育比赛成果详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            SportGameDetailDTO sportGameDetailDTO = iSportGameService.detailById(id);
            if (!(sportGameDetailDTO.getData().getUserId().equals(user.getUserId()))){
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(sportGameDetailDTO);
        }
        SportGameDetailDTO sportGameDetailDTO = iSportGameService.detailById(id);
        return HttpResult.ok(sportGameDetailDTO);


    }

    /**
     * 根据ID删除竞技体育比赛成果
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            SportGameDetailDTO sportGameDetailDTO = iSportGameService.detailById(id);
            if ("03".equals(sportGameDetailDTO.getData().getChkStatus()) || "04".equals(sportGameDetailDTO.getData().getChkStatus())){
                return HttpResult.fail("当前审核状态不允许删除");
            }
            if (!(sportGameDetailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人删除信息");
            }
        }
        if (id != null) {
            int del = iSportGameService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

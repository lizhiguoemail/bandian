package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.jt.DTO.request.AwardsAddDTO;
import com.lhsz.bandian.jt.DTO.request.AwardsDetailDTO;
import com.lhsz.bandian.jt.DTO.request.AwardsUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtAwardsDTO;
import com.lhsz.bandian.jt.entity.Awards;
import com.lhsz.bandian.jt.service.IAwardsService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.List;

/**
 * <p>
 * 获奖情况 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/jt/awards")
public class AwardsController extends BaseController {

    @Autowired
    private IAwardsService iAwardsService;


    /**
     * 查询获奖情况
     *
     * @param awards
     * @return
     */
    @GetMapping()
    public HttpResult ListAwards(Awards awards) {
        startPage();
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            awards.setUserId(user.getUserId());

        }
        List<JtAwardsDTO> jtAwardsDTOS = iAwardsService.listAwards(awards);
        return HttpResult.ok(getDataTable(jtAwardsDTOS));
    }

    /**
     * 根据id进行查询
     *
     * @param awardsId
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult getAwards(@PathVariable("id") String awardsId) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            AwardsDetailDTO awards = iAwardsService.detailById(awardsId);
            if (!(awards.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(awards);
        }
        AwardsDetailDTO awards = iAwardsService.detailById(awardsId);
        if (awards != null) {
            return HttpResult.ok(awards);
        }
        return HttpResult.fail();
    }

    /**
     * 添加
     *
     * @param jtAwardsDTO
     * @return
     */
    @PostMapping()
    public HttpResult addAwards(@RequestBody AwardsAddDTO jtAwardsDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            jtAwardsDTO.getData().setUserId(user.getUserId());
        } else {
            if(jtAwardsDTO.getData().getUserId() == null) {
                return HttpResult.fail("用户信息不能为空");
            }
        }
        jtAwardsDTO.getData().setChkStatus("00");
        jtAwardsDTO.getData().setIsDeleted(0);
        jtAwardsDTO.getData().setAwardsDate(jtAwardsDTO.getData().getAwardsDate().substring(0, 10));
        iAwardsService.addAwards(jtAwardsDTO);
        return HttpResult.ok();
    }

    /**
     * 更新
     *
     * @param jtAwardsDTO
     * @return
     */
    @PutMapping()
    public HttpResult updateAwards(@RequestBody AwardsUpdateDTO jtAwardsDTO) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            if ("03".equals(jtAwardsDTO.getData().getChkStatus()) || "04".equals(jtAwardsDTO.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");

            }
            if (!(jtAwardsDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
        }
        jtAwardsDTO.getData().setAwardsDate(jtAwardsDTO.getData().getAwardsDate().substring(0, 10));
        iAwardsService.updateAwards(jtAwardsDTO);
        return HttpResult.ok();
    }

    /**
     * 删除
     *
     * @param awardsId
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delAwards(@PathVariable("id") String awardsId) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            AwardsDetailDTO awards = iAwardsService.detailById(awardsId);
            if ("03".equals(awards.getData().getChkStatus()) || "04".equals(awards.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许修改");
            }
            if (!(awards.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
        }
        int delAwards = iAwardsService.delAwards(awardsId);
        if (delAwards != 1) {
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }
}

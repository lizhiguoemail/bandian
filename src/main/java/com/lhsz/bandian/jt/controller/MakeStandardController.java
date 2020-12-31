package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.MakeStandardAddDTO;
import com.lhsz.bandian.jt.DTO.request.MakeStandardDetailDTO;
import com.lhsz.bandian.jt.DTO.request.MakeStandardUpdateDTO;
import com.lhsz.bandian.jt.entity.MakeStandard;
import com.lhsz.bandian.jt.service.IMakeStandardService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 主持(参与)制定标准 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/make-standard")
public class MakeStandardController extends BaseController {

    private final IMakeStandardService makeStandardService;
    private final TokenService tokenService;

    @Autowired
    public MakeStandardController(IMakeStandardService makeStandardService, TokenService tokenService) {
        this.makeStandardService = makeStandardService;
        this.tokenService = tokenService;
    }


    /**
     * 查询主持(参与)制定标准
     */
    @GetMapping()
    public HttpResult list(MakeStandard makeStandard) {
        try {
            // 会员只能查询本userId的记录
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                makeStandard.setUserId(user.getUserId());
            }
            startPage();
            return HttpResult.ok(getDataTable(makeStandardService.list(makeStandard)));
        } catch (Exception e) {
            log.debug("查询主持(参与)制定标准异常",e);
            return HttpResult.error("系统内部异常");
        }
    }


    /**
     * 添加主持(参与)制定标准
     */
    @PostMapping()
    public HttpResult add(@RequestBody MakeStandardAddDTO makeStandard){
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if (user.getUserType() == 2) {
                makeStandard.getData().setUserId(user.getUserId());
            } else {
                if(makeStandard.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            makeStandard.getData().setChkStatus("00");
            makeStandardService.add(makeStandard);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加主持(参与)制定标准",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改主持(参与)制定标准
     */
    @PutMapping()
    public HttpResult update(@RequestBody MakeStandardUpdateDTO makeStandard) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人修改信息
            if(user.getUserType() == 2){
                if("03".equals(makeStandard.getData().getChkStatus()) || "04".equals(makeStandard.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许修改");
                }
                if(!(user.getUserId().equals(makeStandard.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            makeStandardService.update(makeStandard);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改主持(参与)制定标准异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 主持(参与)制定标准详情
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        try {
            // 阻止普通用户查询他人信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                MakeStandardDetailDTO makeStandardDetailDTO = makeStandardService.detailById(id);
                if(!(user.getUserId().equals(makeStandardDetailDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许普通用户查询他人信息");
                }
                return HttpResult.ok(makeStandardDetailDTO);
            }
            MakeStandardDetailDTO makeStandardDetailDTO = makeStandardService.detailById(id);
            return HttpResult.ok(makeStandardDetailDTO);
        } catch (Exception e) {
            log.debug("获取主持(参与)制定标准详情异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 根据ID删除主持(参与)制定标准
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            // 阻止普通用户为他人删除信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                MakeStandardDetailDTO makeStandardDetailDTO = makeStandardService.detailById(id);
                if("03".equals(makeStandardDetailDTO.getData().getChkStatus()) || "04".equals(makeStandardDetailDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许删除");
                }
                if(!(user.getUserId().equals(makeStandardDetailDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人删除信息");
                }
            }
            int del = makeStandardService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

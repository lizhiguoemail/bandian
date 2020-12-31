package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.EngnTechProjAddDTO;
import com.lhsz.bandian.jt.DTO.request.EngnTechProjDetailDTO;
import com.lhsz.bandian.jt.DTO.request.EngnTechProjUpdateDTO;
import com.lhsz.bandian.jt.entity.EngnTechProj;
import com.lhsz.bandian.jt.service.IEngnTechProjService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 主持参与工程技术项目 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/jt/engn-tech-proj")
public class EngnTechProjController extends BaseController {

    private final IEngnTechProjService engnTechProjService;
    private final TokenService tokenService;
    private final IUserService userService;

    @Autowired
    public EngnTechProjController(IEngnTechProjService engnTechProjService, TokenService tokenService, IUserService userService) {
        this.engnTechProjService = engnTechProjService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    /**
     * 查询主持参与工程技术项目
     */
    @GetMapping()
    public HttpResult list(EngnTechProj engnTechProj) {
        try {
            // 会员只能查询本userId的记录
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                engnTechProj.setUserId(user.getUserId());
            }
            startPage();
            return HttpResult.ok(getDataTable(engnTechProjService.list(engnTechProj)));
        } catch (Exception e) {
            log.debug("查询主持参与工程技术项目异常",e);
            return HttpResult.error("系统内部异常");
        }
    }


    /**
     * 添加主持参与工程技术项目
     */
    @PostMapping()
    public HttpResult add(@RequestBody EngnTechProjAddDTO engnTechProj){
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if (user.getUserType() == 2) {
                engnTechProj.getData().setUserId(user.getUserId());
            } else {
                if(engnTechProj.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            engnTechProj.getData().setChkStatus("00");
            engnTechProjService.add(engnTechProj);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加主持参与工程技术项目异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改主持参与工程技术项目
     */
    @PutMapping()
    public HttpResult update(@RequestBody EngnTechProjUpdateDTO engnTechProj) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人修改信息
            if(user.getUserType() == 2){
                if("03".equals(engnTechProj.getData().getChkStatus()) || "04".equals(engnTechProj.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许修改");
                }
                if(!(user.getUserId().equals(engnTechProj.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            engnTechProjService.update(engnTechProj);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改主持参与工程技术项目异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 主持参与工程技术项目详情
     *
     * @param id 项目详情id
     * @return 项目详情
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        try {
            // 阻止普通用户查询他人信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                EngnTechProjDetailDTO engnTechProjDTO = engnTechProjService.detailById(id);
                if(!(user.getUserId().equals(engnTechProjDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许普通用户查询他人信息");
                }
            }
            EngnTechProjDetailDTO engnTechProjDTO = engnTechProjService.detailById(id);
            return HttpResult.ok(engnTechProjDTO);
        } catch (Exception e) {
            log.debug("获取主持参与工程技术项目详情异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 根据ID删除主持参与工程技术项目
     *
     * @param id 项目详情id
     * @return 删除数量
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            // 阻止普通用户为他人删除信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                EngnTechProjDetailDTO engnTechProjDTO = engnTechProjService.detailById(id);
                if("03".equals(engnTechProjDTO.getData().getChkStatus()) || "04".equals(engnTechProjDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许删除");
                }
                if(!(user.getUserId().equals(engnTechProjDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人删除信息");
                }
            }
            int del = engnTechProjService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

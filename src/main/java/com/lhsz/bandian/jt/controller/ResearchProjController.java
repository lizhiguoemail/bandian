package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.ResearchProjAddDTO;
import com.lhsz.bandian.jt.DTO.request.ResearchProjDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ResearchProjUpdateDTO;
import com.lhsz.bandian.jt.entity.ResearchProj;
import com.lhsz.bandian.jt.service.IResearchProjService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 主持参与科研项目 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/research-proj")
public class ResearchProjController extends BaseController {
    @Autowired
    private IResearchProjService iResearchProjService;
    /**
     * 查询主持参与科研项目
     */
    @GetMapping()
    public HttpResult list(ResearchProj researchProj) {
        // 会员只能查询本userId的记录
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            researchProj.setUserId(user.getUserId());
        }
        startPage();
        return HttpResult.ok(getDataTable(iResearchProjService.list(researchProj)) );
    }

    /**
     * 保存主持参与科研项目
     */
    @PostMapping()
    public HttpResult add(@RequestBody ResearchProjAddDTO researchProjAddDTO) {

        try {
            User user=tokenService.getLoginUserToUser();
            if (user.getUserType() == 2) {
                researchProjAddDTO.getData().setUserId(user.getUserId());
            } else {
                if(researchProjAddDTO.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            researchProjAddDTO.getData().setChkStatus("00");
            iResearchProjService.add(researchProjAddDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加主持参与科研项目异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改主持参与科研项目
     */
    @PutMapping()
    public HttpResult update(@RequestBody ResearchProjUpdateDTO researchProjUpdateDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if(user.getUserType() == 2){
                if("03".equals(researchProjUpdateDTO.getData().getChkStatus()) || "04".equals(researchProjUpdateDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许添加");
                }
                if(!(user.getUserId().equals(researchProjUpdateDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iResearchProjService.update(researchProjUpdateDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改主持参与科研项目异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 主持参与科研项目详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            ResearchProjDetailDTO researchProjDetailDTO = iResearchProjService.detailById(id);
            if (!(researchProjDetailDTO.getData().getUserId().equals(user.getUserId()))){
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(researchProjDetailDTO);
        }
        ResearchProjDetailDTO researchProjDetailDTO = iResearchProjService.detailById(id);
        return HttpResult.ok(researchProjDetailDTO);

    }

    /**
     * 根据ID删除主持参与科研项目
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            ResearchProjDetailDTO researchProjDetailDTO = iResearchProjService.detailById(id);
            if ("03".equals(researchProjDetailDTO.getData().getChkStatus()) || "04".equals(researchProjDetailDTO.getData().getChkStatus())){
                return HttpResult.fail("当前审核状态不允许删除");
            }
            if (!(researchProjDetailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人删除信息");
            }
        }
        if (id != null) {
            int del = iResearchProjService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

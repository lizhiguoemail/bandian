package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.ThesisAddDTO;
import com.lhsz.bandian.jt.DTO.request.ThesisDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ThesisUpdateDTO;
import com.lhsz.bandian.jt.entity.Thesis;
import com.lhsz.bandian.jt.service.IThesisService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 论文 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/thesis")
public class ThesisController extends BaseController {
    @Autowired
    private IThesisService iThesisService;
    /**
     * 查询论文
     */
    @GetMapping()
    public HttpResult list(Thesis thesis) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            thesis.setUserId(user.getUserId());

        }
        startPage();
        return HttpResult.ok(getDataTable(iThesisService.list(thesis)) );
    }

    /**
     * 保存论文
     */
    @PostMapping()
    public HttpResult add(@RequestBody ThesisAddDTO thesisAddDTO) {

        try {
            User user=tokenService.getLoginUserToUser();
            // 普通用户不能访问该接口
            if (user.getUserType() == 2) {
                thesisAddDTO.getData().setUserId(user.getUserId());
            } else {
                if(thesisAddDTO.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            thesisAddDTO.getData().setChkStatus("00");
            iThesisService.add(thesisAddDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加论文异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改论文
     */
    @PutMapping()
    public HttpResult update(@RequestBody ThesisUpdateDTO thesisUpdateDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if(user.getUserType() == 2){
                if("03".equals(thesisUpdateDTO.getData().getChkStatus()) || "04".equals(thesisUpdateDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许添加");
                }
                if(!(user.getUserId().equals(thesisUpdateDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iThesisService.update(thesisUpdateDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改论文异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 论文详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            ThesisDetailDTO thesisDetailDTO = iThesisService.detailById(id);
            if (!(thesisDetailDTO.getData().getUserId().equals(user.getUserId()))){
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(thesisDetailDTO);
        }
        ThesisDetailDTO thesisDetailDTO = iThesisService.detailById(id);
        return HttpResult.ok(thesisDetailDTO);
    }

    /**
     * 根据ID删除论文
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            ThesisDetailDTO thesisDetailDTO = iThesisService.detailById(id);
            if ("03".equals(thesisDetailDTO.getData().getChkStatus()) || "04".equals(thesisDetailDTO.getData().getChkStatus())){
                return HttpResult.fail("当前审核状态不允许删除");
            }
            if (!(thesisDetailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人删除信息");
            }
        }
        if (id != null) {
            int del = iThesisService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

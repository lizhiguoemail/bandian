package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.QaCertAddDTO;
import com.lhsz.bandian.jt.DTO.request.QaCertDetailDTO;
import com.lhsz.bandian.jt.DTO.request.QaCertUpdateDTO;
import com.lhsz.bandian.jt.entity.QaCert;
import com.lhsz.bandian.jt.service.IQaCertService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 资质证书 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/qa-cert")
public class QaCertController extends BaseController {
    @Autowired
    private IQaCertService iQaCertService;
    /**
     * 查询资质证书
     */
    @GetMapping()
    public HttpResult list(QaCert qaCert) {
        // 会员只能查询本userId的记录
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            qaCert.setUserId(user.getUserId());
        }
        startPage();
        return HttpResult.ok(getDataTable(iQaCertService.list(qaCert)) );
    }

    /**
     * 保存资质证书
     */
    @PostMapping()
    public HttpResult add(@RequestBody QaCertAddDTO qaCertAddDTO) {
        try {
            // 普通用户不能访问该接口
            User user=tokenService.getLoginUserToUser();
            if (user.getUserType() == 2) {
                qaCertAddDTO.getData().setUserId(user.getUserId());
            } else {
                if(qaCertAddDTO.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            qaCertAddDTO.getData().setChkStatus("00");
            iQaCertService.add(qaCertAddDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加资质证书异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改资质证书
     */
    @PutMapping()
    public HttpResult update(@RequestBody QaCertUpdateDTO qaCertUpdateDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if(user.getUserType() == 2){
                if("03".equals(qaCertUpdateDTO.getData().getChkStatus()) || "04".equals(qaCertUpdateDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许添加");
                }
                if(!(user.getUserId().equals(qaCertUpdateDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iQaCertService.update(qaCertUpdateDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改资质证书异常",e);
            return HttpResult.error("系统内部异常");
        }

    }

    /**
     * 资质证书详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            QaCertDetailDTO qaCertDTO = iQaCertService.detailById(id);
            if (!(qaCertDTO.getData().getUserId().equals(user.getUserId()))){
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(qaCertDTO);
        }
        QaCertDetailDTO qaCertDTO = iQaCertService.detailById(id);
        return HttpResult.ok(qaCertDTO);
    }

    /**
     * 根据ID删除资质证书
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            QaCertDetailDTO qaCertDTO = iQaCertService.detailById(id);
            if ("03".equals(qaCertDTO.getData().getChkStatus()) || "04".equals(qaCertDTO.getData().getChkStatus())){
                return HttpResult.fail("当前审核状态不允许删除");
            }
            if (!(qaCertDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人删除信息");
            }
        }
        if (id != null) {
            int del = iQaCertService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

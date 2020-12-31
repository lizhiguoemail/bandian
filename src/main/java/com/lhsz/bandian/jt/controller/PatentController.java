package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.PatentAddDTO;
import com.lhsz.bandian.jt.DTO.request.PatentDetailDTO;
import com.lhsz.bandian.jt.DTO.request.PatentUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.PatentDTO;
import com.lhsz.bandian.jt.entity.Patent;
import com.lhsz.bandian.jt.service.IPatentService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 专利(著作权) 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/patent")
public class PatentController extends BaseController {
    @Autowired
    private IPatentService iPatentService;

    /**
     * 查询专利(著作权)
     */
    @GetMapping()
    public HttpResult list(Patent patent) {
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            patent.setUserId(user.getUserId());
        }
        startPage();
        List<PatentDTO> list = iPatentService.list(patent);

        return HttpResult.ok(getDataTable(list));

    }

    /**
     * 保存专利(著作权)
     */
    @PostMapping()
    public HttpResult add(@RequestBody PatentAddDTO patentAddDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            if (user.getUserType() == 2) {
                patentAddDTO.getData().setUserId(user.getUserId());
            } else {
                if(patentAddDTO.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            patentAddDTO.getData().setChkStatus("00");
            iPatentService.add(patentAddDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加专利(著作权)异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改专利(著作权)
     */
    @PutMapping()
    public HttpResult update(@RequestBody PatentUpdateDTO patentUpdateDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if(user.getUserType() == 2){
                if("03".equals(patentUpdateDTO.getData().getChkStatus()) || "04".equals(patentUpdateDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许添加");
                }
                if(!(user.getUserId().equals(patentUpdateDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iPatentService.update(patentUpdateDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改专利(著作权)异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 专利(著作权)详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            PatentDetailDTO patentDetailDTO = iPatentService.detailById(id);
            if (!(patentDetailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(patentDetailDTO);
        }
        PatentDetailDTO patentDetailDTO = iPatentService.detailById(id);
        if (patentDetailDTO != null) {
            return HttpResult.ok(patentDetailDTO);
        }
        return HttpResult.fail();

    }

    /**
     * 根据ID删除专利(著作权)
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2) {
            PatentDetailDTO patentDetailDTO = iPatentService.detailById(id);
            if ("03".equals(patentDetailDTO.getData().getChkStatus()) || "04".equals(patentDetailDTO.getData().getChkStatus())) {
                return HttpResult.fail("当前审核状态不允许删除");
            }
            if (!(patentDetailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人删除信息");
            }
        }
        if (id != null) {
            int del = iPatentService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

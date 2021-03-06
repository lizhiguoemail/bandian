package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.WorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.WorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.WorkUpdateDTO;
import com.lhsz.bandian.jt.entity.Work;
import com.lhsz.bandian.jt.service.IWorkService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 工作经历 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/work")
public class WorkController extends BaseController {
    @Autowired
    private IWorkService iWorkService;
    /**
     * 查询工作经历
     */
    @GetMapping()
    public HttpResult list(Work work) {
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            work.setUserId(user.getUserId());
        }
        startPage();
        return HttpResult.ok(getDataTable(iWorkService.list(work)) );
    }

    /**
     * 保存工作经历
     */
    @PostMapping()
    public HttpResult add(@RequestBody WorkAddDTO work) {

        try {
            User user=tokenService.getLoginUserToUser();
            if (user.getUserType() == 2) {
                work.getData().setUserId(user.getUserId());
            } else {
                if(work.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            work.getData().setChkStatus("00");
            iWorkService.add(work);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加工作经历异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改工作经历
     */
    @PutMapping()
    public HttpResult update(@RequestBody WorkUpdateDTO workDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if(user.getUserType() == 2){
                if("03".equals(workDTO.getData().getChkStatus()) || "04".equals(workDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许添加");
                }
                if(!(user.getUserId().equals(workDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iWorkService.update(workDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改工作经历异常",e);
            return HttpResult.error("系统内部异常");
        }

    }

    /**
     * 工作经历详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            WorkDetailDTO workDTO = iWorkService.detailById(id);
            if (!(workDTO.getData().getUserId().equals(user.getUserId()))){
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(workDTO);
        }
        WorkDetailDTO workDTO = iWorkService.detailById(id);
        return HttpResult.ok(workDTO);


    }

    /**
     * 根据ID删除工作经历
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            WorkDetailDTO workDTO = iWorkService.detailById(id);
            if ("03".equals(workDTO.getData().getChkStatus()) || "04".equals(workDTO.getData().getChkStatus())){
                return HttpResult.fail("当前审核状态不允许删除");
            }
            if (!(workDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人删除信息");
            }
        }
        if (id != null) {
            int del = iWorkService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

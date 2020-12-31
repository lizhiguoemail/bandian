package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.ResultUsedAddDTO;
import com.lhsz.bandian.jt.DTO.request.ResultUsedDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ResultUsedUpdateDTO;
import com.lhsz.bandian.jt.entity.ResultUsed;
import com.lhsz.bandian.jt.service.IResultUsedService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 成果被批示、采纳、运用和推广 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/result-used")
public class ResultUsedController extends BaseController {
    @Autowired
    private IResultUsedService iResultUsedService;
    /**
     * 查询成果被批示、采纳、运用和推广
     */
    @GetMapping()
    public HttpResult list(ResultUsed resultUsed) {
        // 会员只能查询本userId的记录
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            resultUsed.setUserId(user.getUserId());
        }
        startPage();
        return HttpResult.ok(getDataTable(iResultUsedService.list(resultUsed)) );
    }

    /**
     * 保存成果被批示、采纳、运用和推广
     */
    @PostMapping()
    public HttpResult add(@RequestBody ResultUsedAddDTO resultUsedAddDTO) {
        try {
            // 普通用户不能访问该接口
            User user=tokenService.getLoginUserToUser();
            if (user.getUserType() == 2) {
                resultUsedAddDTO.getData().setUserId(user.getUserId());
            } else {
                if(resultUsedAddDTO.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            resultUsedAddDTO.getData().setChkStatus("00");
            iResultUsedService.add(resultUsedAddDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加成果被批示、采纳、运用和推广 前端控制器异常",e);
            return HttpResult.error("系统内部异常");
        }

    }

    /**
     * 修改成果被批示、采纳、运用和推广
     */
    @PutMapping()
    public HttpResult update(@RequestBody ResultUsedUpdateDTO resultUsedUpdateDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if(user.getUserType() == 2){
                if("03".equals(resultUsedUpdateDTO.getData().getChkStatus()) || "04".equals(resultUsedUpdateDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许添加");
                }
                if(!(user.getUserId().equals(resultUsedUpdateDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iResultUsedService.update(resultUsedUpdateDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改成果被批示、采纳、运用和推广 前端控制器异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 成果被批示、采纳、运用和推广详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            ResultUsedDetailDTO resultUsedDTO = iResultUsedService.detailById(id);
            if (!(resultUsedDTO.getData().getUserId().equals(user.getUserId()))){
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(resultUsedDTO);
        }
        ResultUsedDetailDTO resultUsedDTO = iResultUsedService.detailById(id);
        return HttpResult.ok(resultUsedDTO);
    }

    /**
     * 根据ID删除成果被批示、采纳、运用和推广
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            ResultUsedDetailDTO resultUsedDTO = iResultUsedService.detailById(id);
            if ("03".equals(resultUsedDTO.getData().getChkStatus()) || "04".equals(resultUsedDTO.getData().getChkStatus())){
                return HttpResult.fail("当前审核状态不允许删除");
            }
            if (!(resultUsedDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人删除信息");
            }
        }
        if (id != null) {
            int del = iResultUsedService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

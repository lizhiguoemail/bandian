package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.WritingsAddDTO;
import com.lhsz.bandian.jt.DTO.request.WritingsDetailDTO;
import com.lhsz.bandian.jt.DTO.request.WritingsUpdateDTO;
import com.lhsz.bandian.jt.entity.Writings;
import com.lhsz.bandian.jt.service.IWritingsService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 著(译)作(教材) 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/writings")
public class WritingsController extends BaseController {
    @Autowired
    private IWritingsService iWritingsService;
    /**
     * 查询著(译)作(教材)
     */
    @GetMapping()
    public HttpResult list(Writings writings) {
        startPage();
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            writings.setUserId(user.getUserId());
        }
        return HttpResult.ok(getDataTable(iWritingsService.list(writings)) );
    }

    /**
     * 保存著(译)作(教材)
     */
    @PostMapping()
    public HttpResult add(@RequestBody WritingsAddDTO writingsDTO) {

        try {
            User user=tokenService.getLoginUserToUser();
            if (user.getUserType() == 2) {
                writingsDTO.getData().setUserId(user.getUserId());
            } else {
                if(writingsDTO.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            writingsDTO.getData().setChkStatus("00");
            iWritingsService.add(writingsDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加著(译)作(教材)异常",e);
            return HttpResult.error("系统内部异常");
        }

    }

    /**
     * 修改著(译)作(教材)
     */
    @PutMapping()
    public HttpResult update(@RequestBody WritingsUpdateDTO writingsDTO) {

        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if(user.getUserType() == 2){
                if("03".equals(writingsDTO.getData().getChkStatus()) || "04".equals(writingsDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许添加");
                }
                if(!(user.getUserId().equals(writingsDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iWritingsService.update(writingsDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改著(译)作(教材)异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 著(译)作(教材)详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            WritingsDetailDTO writingsDTO = iWritingsService.detailById(id);
            if (!(writingsDTO.getData().getUserId().equals(user.getUserId()))){
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(writingsDTO);
        }
        WritingsDetailDTO writingsDTO = iWritingsService.detailById(id);
        return HttpResult.ok(writingsDTO);


    }

    /**
     * 根据ID删除著(译)作(教材)
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            WritingsDetailDTO writingsDTO = iWritingsService.detailById(id);
            if ("03".equals(writingsDTO.getData().getChkStatus()) || "04".equals(writingsDTO.getData().getChkStatus())){
                return HttpResult.fail("当前审核状态不允许删除");
            }
            if (!(writingsDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人删除信息");
            }
        }
        if (id != null) {
            int del = iWritingsService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

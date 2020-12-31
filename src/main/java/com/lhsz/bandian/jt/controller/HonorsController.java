package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.HonorsAddDTO;
import com.lhsz.bandian.jt.DTO.request.HonorsDetailDTO;
import com.lhsz.bandian.jt.DTO.request.HonorsUpdateDTO;
import com.lhsz.bandian.jt.entity.Honors;
import com.lhsz.bandian.jt.service.IHonorsService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 获得荣誉 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/jt/honors")
public class HonorsController extends BaseController {

    private final IHonorsService honorsService;
    private final TokenService tokenService;

    @Autowired
    public HonorsController(IHonorsService honorsService, TokenService tokenService) {
        this.honorsService = honorsService;
        this.tokenService = tokenService;
    }

    /**
     * 查询获得荣誉
     */
    @GetMapping()
    public HttpResult list(Honors honors) {
        try {
            // 会员只能查询本userId的记录
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                honors.setUserId(user.getUserId());
            }
            startPage();
            return HttpResult.ok(getDataTable(honorsService.list(honors)));
        } catch (Exception e) {
            log.debug("查询获得荣誉异常",e);
            return HttpResult.error("系统内部异常");
        }
    }


    /**
     * 添加获得荣誉
     */
    @PostMapping()
    public HttpResult add(@RequestBody HonorsAddDTO honors){
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if (user.getUserType() == 2) {
                honors.getData().setUserId(user.getUserId());
            } else {
                if(honors.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            honors.getData().setChkStatus("00");
            honorsService.add(honors);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加获得荣誉异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改获得荣誉
     */
    @PutMapping()
    public HttpResult update(@RequestBody HonorsUpdateDTO honors) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人修改信息
            if(user.getUserType() == 2){
                if("03".equals(honors.getData().getChkStatus()) || "04".equals(honors.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许修改");
                }

                if(!(user.getUserId().equals(honors.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }

            }
            honorsService.update(honors);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改获得荣誉异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 获得荣誉详情
     *
     * @param id 荣誉详情id
     * @return 荣誉详情
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        try {
            // 阻止普通用户查询他人信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                HonorsDetailDTO honorsDetailDTO = honorsService.detailById(id);
                if(!(user.getUserId().equals(honorsDetailDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许普通用户查询他人信息");
                }
                return HttpResult.ok(honorsDetailDTO);
            }
            HonorsDetailDTO honorsDTO = honorsService.detailById(id);
            return HttpResult.ok(honorsDTO);
        } catch (Exception e) {
            log.debug("获得荣誉详情异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 根据ID删除获得荣誉
     *
     * @param id 荣誉详情id
     * @return 删除数量
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            // 阻止普通用户为他人删除信息
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2) {
                HonorsDetailDTO honorsDetailDTO = honorsService.detailById(id);
                if("03".equals(honorsDetailDTO.getData().getChkStatus()) || "04".equals(honorsDetailDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许删除");
                }

                if(!(user.getUserId().equals(honorsDetailDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人删除信息");
                }
            }
            int del = honorsService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }

}

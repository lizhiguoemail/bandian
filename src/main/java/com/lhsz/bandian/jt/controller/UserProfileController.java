package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.UserProfileAddDTO;
import com.lhsz.bandian.jt.DTO.request.UserProfileDetailDTO;
import com.lhsz.bandian.jt.DTO.request.UserProfileUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.UserProfileDTO;
import com.lhsz.bandian.jt.entity.UserProfile;
import com.lhsz.bandian.jt.service.IUserProfileService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 基本信息 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/user-profile")
public class UserProfileController extends BaseController {
    @Autowired
    private IUserProfileService iUserProfileService;
    /**
     * 查询基本信息
     */
    @GetMapping()
    public HttpResult list(UserProfile userProfile) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            userProfile.setUserId(user.getUserId());
        }
        startPage();
        try {
            List<UserProfileDTO> list = iUserProfileService.list(userProfile);
            return HttpResult.ok(getDataTable(list));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.fail();
    }

    /**
     * 保存基本信息
     */
    @PostMapping()
    public HttpResult add(@RequestBody UserProfileAddDTO userProfileAddDTO) {
        try {
            // 普通用户不能访问该接口
            User user=tokenService.getLoginUserToUser();
            if (user.getUserType() == 2) {
                userProfileAddDTO.getData().setUserId(user.getUserId());
            } else {
                if(userProfileAddDTO.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            userProfileAddDTO.getData().setChkStatus("00");
            iUserProfileService.add(userProfileAddDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加基本信息)异常",e);
            return HttpResult.error("系统内部异常");
        }

    }

    /**
     * 修改基本信息
     */
    @PutMapping()
    public HttpResult update(@RequestBody UserProfileUpdateDTO userProfileUpdateDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if(user.getUserType() == 2){
                if("03".equals(userProfileUpdateDTO.getData().getChkStatus()) || "04".equals(userProfileUpdateDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许添加");
                }
                if(!(user.getUserId().equals(userProfileUpdateDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iUserProfileService.update(userProfileUpdateDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改基本信息异常",e);
            return HttpResult.error("系统内部异常");
        }

    }

    /**
     * 基本信息详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            UserProfileDetailDTO userProfileDetailDTO = iUserProfileService.detailById(id);
            if (!(userProfileDetailDTO.getData().getUserId().equals(user.getUserId()))){
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(userProfileDetailDTO);
        }
        UserProfileDetailDTO userProfileDetailDTO = iUserProfileService.detailById(id);
        return HttpResult.ok(userProfileDetailDTO);


    }

    /**
     * 根据用户id查
     *
     * @param
     * @return
     */
    @GetMapping("/userId")
    public HttpResult selectByUserId() {
        User user = tokenService.getLoginUserToUser();
        /*if (user!=null){
            if (user.getUserType() == 2){
                UserProfileDetailDTO userProfileDetailDTO = iUserProfileService.detailById(user.getUserId());
                if (!(userProfileDetailDTO.getData().getUserId().equals(user.getUserId()))){
                    return HttpResult.fail("不允许普通用户查询他人信息");
                }
            }
        }*/
        UserProfileDetailDTO userProfileDetailDTO = iUserProfileService.detailById(user.getUserId());
        return HttpResult.ok(userProfileDetailDTO);


    }

    /**
     * 根据ID删除基本信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            UserProfileDetailDTO userProfileDetailDTO = iUserProfileService.detailById(id);
            if ("03".equals(userProfileDetailDTO.getData().getChkStatus()) || "04".equals(userProfileDetailDTO.getData().getChkStatus())){
                return HttpResult.fail("当前审核状态不允许删除");
            }
            if (!(userProfileDetailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人删除信息");
            }
        }
        if (id != null) {
            int del = iUserProfileService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}

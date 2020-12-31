package com.lhsz.bandian.portal.controller;

import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.UserProfileDetailDTO;
import com.lhsz.bandian.jt.entity.UserProfile;
import com.lhsz.bandian.jt.service.IUserProfileService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.DTO.result.UserDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IAttachService;
import com.lhsz.bandian.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author lizhiguo
 * 2020/7/30 14:35
 */

@RestController
@RequestMapping("/portal")
public class PortalController extends BaseController {
    /**
     * 用户服务
     */
    IUserService userService;
    /**
     * 用户资料
     */
    IUserProfileService userProfileService;
    /**
     * 附件服务
     */
    IAttachService attachService;

    /**
     * 初始化
     * @param userService 用户服务
     * @param userProfileService 用户资料服务
     * @param attachService  附件服务
     */
    public PortalController(IUserService userService, IUserProfileService userProfileService, IAttachService attachService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.attachService = attachService;
    }
}

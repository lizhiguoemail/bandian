package com.lhsz.bandian.portal.controller;

import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.UserProfileDetailDTO;
import com.lhsz.bandian.jt.entity.UserProfile;
import com.lhsz.bandian.jt.service.IUserProfileService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.DTO.result.JuryRegisterDTO;
import com.lhsz.bandian.sys.DTO.result.UserDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IAttachService;
import com.lhsz.bandian.sys.service.IUserService;
import com.lhsz.bandian.utils.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author lizhiguo
 * 2020/7/30 14:35
 */

@RestController("PortalUser")
@RequestMapping("/portal/user")
@Slf4j
public class UserController extends BaseController {
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
    public UserController(IUserService userService, IUserProfileService userProfileService, IAttachService attachService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.attachService = attachService;
    }

    /**
     * 注册会员
     * @param userDTO 用户信息
     * @return 返回注册结果
     */
    @PostMapping("/register")
    public HttpResult register(@RequestBody UserDTO userDTO) {
        if (StringUtils.isEmpty(userDTO.getClientId())) {
            return HttpResult.fail("缺少clientId参数！");
        }
        User byUsername = userService.findByUsername(userDTO.getUserName());
        if (byUsername != null) {
            return HttpResult.fail("用户名已存在");
        }
        User byMobile = userService.findByMobile(userDTO.getPhoneNumber());

        if (byMobile != null) {
            return HttpResult.fail("该手机号已被注册");
        }
        UserProfile byCertNo = userProfileService.findByCertNo(userDTO.getCertNo());
        if (byCertNo != null) {
            return HttpResult.fail("该身份证号已被使用");
        }
        userService.regist(userDTO);
        return HttpResult.ok();
    }

    /**
     * 注册评委会/主管部门用户
     * @param juryRegisterDTO 注册信息
     * @return 返回注册结果
     */
    @PostMapping("/registerAdmin")
    public HttpResult registerAdmin(@RequestBody JuryRegisterDTO juryRegisterDTO) {
        if (StringUtils.isEmpty(juryRegisterDTO.getClientId())) {
            return HttpResult.fail("缺少clientId参数！");
        }
        User byUsername = userService.findByUsername(juryRegisterDTO.getUserName());
        if (byUsername != null) {
            return HttpResult.fail("用户名已存在");
        }
        User byMobile = userService.findByMobile(juryRegisterDTO.getPhoneNumber());

        if (byMobile != null) {
            return HttpResult.fail("该手机号已被注册");
        }

        userService.registerAdmin(juryRegisterDTO);
        return HttpResult.ok();
    }

    /**
     * 查询用户资料
     * @return 返回用户资料
     */
    @GetMapping("/profile")
    public HttpResult getUserProfile() {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() != 2){
            return HttpResult.fail("用户类型有误，请登录会员");
        }
        UserProfileDetailDTO userProfileDetailDTO = userProfileService.detailById(user.getUserId());
        return HttpResult.ok(userProfileDetailDTO);
    }

    /**
     * 上传用户证件照
     * @param file 附件
     * @return 返回上传结果
     * @throws IOException 异常
     */
    @PostMapping("/cert-photo")
    public HttpResult uploadCertPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return HttpResult.fail();
        }

        // 判断证件照文件大小是否合规
        long fileSize = file.getSize();
        long minSize = 30720L;
        long maxSize = 1048576L;
        if((fileSize<minSize) || (fileSize>maxSize)){
            return HttpResult.fail("证件照文件大小不符合要求");
        }

        // 判断图像宽高
        MultipartFile multipartFile = file;
        try {
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            if (bufferedImage == null) {
                return HttpResult.fail("请上传文件");
            }
            Integer width = bufferedImage.getWidth();
            Integer height = bufferedImage.getHeight();
            if(width < 215 || height <300){
                return HttpResult.fail("证件照宽高小于最小限制");
            }
        } catch (Exception e) {
            log.error("图像不合法",e);
            return HttpResult.fail("图像不合法");
        }

        Attach attach = attachService.upload(file);

        User user = tokenService.getLoginUserToUser();
        userProfileService.updateCertPhotoById(user.getUserId(), attach.getFilePath());

        return UploadUtils.getHttpResult(attach);
    }
}

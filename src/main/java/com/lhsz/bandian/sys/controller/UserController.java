package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.AOP.OperLog;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.UploadExcleDTO;
import com.lhsz.bandian.config.properties.BandianProperties;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.sys.DTO.AppData;
import com.lhsz.bandian.sys.DTO.query.QueryUserDTO;
import com.lhsz.bandian.sys.DTO.result.PwdUpdateDTO;
import com.lhsz.bandian.sys.DTO.result.UserDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IAttachService;
import com.lhsz.bandian.sys.service.IUserService;
import com.lhsz.bandian.utils.Convert;
import com.lhsz.bandian.utils.SecurityUtils;
import com.lhsz.bandian.utils.UploadUtils;
import com.lhsz.bandian.utils.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@Slf4j
@RestController
@RequestMapping("/systems/user")
@Validated
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IAttachService attachService;

    /**
     * 用户资料
     */
//    IUserProfileService userProfileService;
    @PreAuthorize("hasAuthority('systems:user:add')")
    @PostMapping()
    public HttpResult add(@RequestBody UserDTO userDTO) {
        if (StringUtils.isEmpty(userDTO.getClientId())) {
            return HttpResult.fail("缺少clientId参数！");
        }
        User byUsername = userService.findByUsername(userDTO.getUserName());
        if (byUsername != null) {
            return HttpResult.fail("用户名已被注册");
        }
        if (userDTO.getPhoneNumber() != null && !"".equals(userDTO.getPhoneNumber())) {
            User byMobile = userService.findByMobile(userDTO.getPhoneNumber());

            if (byMobile != null) {
                return HttpResult.fail("该手机号已被注册");
            }
        }
        if (userDTO.getEmail() != null && !"".equals(userDTO.getEmail())) {
            User userEmail = userService.findByEmail(userDTO.getEmail());
            if (userEmail != null) {
                return HttpResult.fail("邮箱已被注册");
            }
        }


        userService.addUserAndRole(userDTO);
        log.info("添加用户成功！");
        return HttpResult.ok();
    }


    @PreAuthorize("hasAuthority('systems:user:update')")
    @PutMapping()
    public HttpResult update(@RequestBody UserDTO userDTO) {
        User olduser = userService.getById(userDTO.getUserId());
        if (userDTO.getPhoneNumber() != null && !"".equals(userDTO.getPhoneNumber())) {
            if (!userDTO.getPhoneNumber().equals(olduser.getPhoneNumber())) {
                User byMobile = userService.findByMobile(userDTO.getPhoneNumber());
                if (byMobile != null) {
                    return HttpResult.fail("该手机号已被注册");
                }
            }
        }
        if (userDTO.getEmail() != null && !"".equals(userDTO.getEmail())) {
            if (!Convert.toUpperCase(userDTO.getEmail()).equals(olduser.getNormalizedEmail())) {
                User userEmail = userService.findByEmail(userDTO.getEmail());
                if (userEmail != null) {
                    return HttpResult.fail("邮箱已被注册");
                }
            }
        }
        userService.updateUserAndRole(userDTO);
        return HttpResult.ok();
    }

    /**
     * 获取菜单数据
     *
     * @return 菜单列表 AppData
     */
    @GetMapping("/app-data")
    public HttpResult app_data() {
        AppData appData = userService.getApp_data();
        return HttpResult.ok(appData);
    }

    /**
     * 用户列表
     *
     * @param user 用户查询条件
     * @return 用户集合
     */
    @PreAuthorize("hasAuthority('systems:user:list')")
    @GetMapping()
    public HttpResult listUser(QueryUserDTO user) {
        startPage();
        List<UserDTO> userList = userService.listQueryUserDTO(user);
        return HttpResult.ok(getDataTable(userList));
    }

    /**
     * 用户详情
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @PreAuthorize("hasAuthority('systems:user:show')")
    @GetMapping("/{id}")
//    @OperLog(operModul = "用户详情",operType = OperLog.Type.ADD,operDesc = "查看用户数据") 示例代码，查看详情不需要添加日志记录
    public HttpResult showUser(@PathVariable("id") String id) {
        UserDTO user = userService.getUserDTO(id);
        return HttpResult.ok(user);
    }

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 成功或失败
     */
    @PreAuthorize("hasAuthority('systems:user:delete')")
    @DeleteMapping("/{id}")
    public HttpResult deleteUser(@PathVariable("id") String id) {
        if (userService.del(id) != 1) {
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }

    /**
     * 重置密码
     *
     * @param id 用户ID
     * @return HttpResult
     */
    @PutMapping("/pwd/{id}")
    @PreAuthorize("hasAuthority('systems:user:resertPwd')")
    public HttpResult resertPwd(@PathVariable("id") String id) {
        String pwd = "111111";
        User user = userService.getById(id);
        user.setPasswordHash(SecurityUtils.encryptPassword(pwd));
        if (userService.updateById(user)) {
            return HttpResult.succee();
        } else {
            return HttpResult.fail();
        }

    }

    @PostMapping("/avatar")
    public HttpResult avatar(@RequestParam("file") MultipartFile file) {
        Attach attach = attachService.uploadAvatar(file);
        User userCache = tokenService.getLoginUserToUser();
        User user = userService.findByUsername(userCache.getUserName());
        user.setAvatar(attach.getFilePath());
        userService.updateById(user);
        return HttpResult.ok(attach);
    }

    @GetMapping("/base")
    public HttpResult base() {
        UserDTO userDTO = userService.getUserDTO();
        return HttpResult.ok(userDTO);
    }

    @PutMapping("/base")
    public HttpResult base(@RequestBody UserDTO userDTO) {
        if (userService.updateByIdForUserDTO(userDTO)) {
            return HttpResult.succee();
        }
        return HttpResult.fail();
    }

    /**
     * 修改密码
     *
     * @param pwdDTO 密码对象
     * @return 成功或失败
     */
    @PostMapping("/pwd")
    // @PreAuthorize("hasAuthority('systems:user:pwd')")
    public HttpResult updatePwd(@RequestBody PwdUpdateDTO pwdDTO) {
        User userCache = tokenService.getLoginUserToUser();
        if (SecurityUtils.matchesPassword(pwdDTO.getOldPassword(), userCache.getPasswordHash())) {
            User user = userService.getById(userCache.getUserId());
            user.setPasswordHash(SecurityUtils.encryptPassword(pwdDTO.getNewPassword()));
            if (userService.updateById(user)) {
                log.info("密码修改成功!");
                //更新缓存 2020-8-11 去掉缓存更新
                tokenService.updateLoginUserToUser(user);
                log.info("用户缓存已更新!");
                return HttpResult.succee();
            }
        } else {
            log.info("密码修改失败!旧密码不正确");
            return HttpResult.fail("旧密码不正确");
        }
        return HttpResult.fail();

    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('systems:user:deleteAll')")
    public HttpResult delete(@RequestBody() String ids) {
        ids = ids.replace("\"", "");
        String[] split = ids.split(",");
        if (userService.removeByIds(Arrays.asList(split))) {
            return HttpResult.succee();
        } else {
            return HttpResult.fail();
        }
    }

    /**
     * 获取当前用户普通信息
     */
    @GetMapping("/getLoginUser")
    public HttpResult getLoginCount() {
        User loginUserToUser = tokenService.getLoginUserToUser();
        UserDTO user = userService.getUserDTO(loginUserToUser.getUserId());
        UserDTO userDTO = new UserDTO();
        userDTO.setUserType(user.getUserType());
        userDTO.setLoginCount(user.getLoginCount());
        return HttpResult.ok(userDTO);
    }

    @OperLog(operModul = "导出用户数据",operType = OperLog.Type.OTHER,operDesc = "")
//    @PreAuthorize("hasAuthority('systems:user:export')")
    @GetMapping("/export")
    public HttpResult export(QueryUserDTO user)
    {
        List<UserDTO> userList = userService.listQueryUserDTO(user);
        ExcelUtil<UserDTO> util = new ExcelUtil<>(UserDTO.class);
        return util.exportExcel(userList, "用户数据");
    }

    @GetMapping("/importTemplate")
    public HttpResult importTemplate()
    {
        ExcelUtil<UserDTO> util = new ExcelUtil<>(UserDTO.class);
        return util.importTemplateExcel("用户数据");
    }
    @OperLog(operModul = "用户管理",operType = OperLog.Type.OTHER,operDesc = "导入用户数据")
    @PreAuthorize("hasAuthority('system:user:import')")
    @PostMapping("/importData")
    public HttpResult importData(@RequestBody UploadExcleDTO uploadExcleDTO) throws Exception
    {
        ExcelUtil<UserDTO> util = new ExcelUtil<>(UserDTO.class);
        String filePathName=BandianProperties.uploadPath + File.separator + uploadExcleDTO.getFileName();
        File file= UploadUtils.createFile(filePathName);
        List<UserDTO> userList = util.importExcel(new FileInputStream(file));
        String message = userService.importUser(userList, uploadExcleDTO.isUpdateSupport());
        UploadUtils.deleteFileByFile(file);
        return HttpResult.ok(message);
    }

}

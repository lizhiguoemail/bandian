package com.lhsz.bandian.sys.DTO.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="User对象", description="用户")
public class UserDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户类型(1办公用户，2企业用户，3车辆用户)")
    private Integer userType;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "标准化用户名")
    private String normalizedUserName;

    @ApiModelProperty(value = "安全邮箱")
    private String email;

    @ApiModelProperty(value = "标准化邮箱")
    private String normalizedEmail;

    @ApiModelProperty(value = "邮箱已确认")
    private Boolean emailConfirmed;

    @ApiModelProperty(value = "安全手机")
    private String phoneNumber;

    @ApiModelProperty(value = "手机已确认")
    private Boolean phoneNumberConfirmed;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "密码散列")
    private String passwordHash;

    @ApiModelProperty(value = "安全码")
    private String safePassword;

    @ApiModelProperty(value = "安全码散列")
    private String safePasswordHash;

    @ApiModelProperty(value = "启用两阶段认证")
    private Boolean twoFactorEnabled;

    @ApiModelProperty(value = "启用")
    private Boolean enabled;

    @ApiModelProperty(value = "冻结时间")
    private LocalDateTime disabledTime;

    @ApiModelProperty(value = "启用锁定")
    private Boolean lockoutEnabled;

    @ApiModelProperty(value = "锁定截止")
    private LocalDateTime lockoutEnd;

    @ApiModelProperty(value = "登录失败次数")
    private Integer accessFailedCount;

    @ApiModelProperty(value = "登录次数")
    private Integer loginCount;

    @ApiModelProperty(value = "注册Ip")
    private String registerIp;

    @ApiModelProperty(value = "上次登陆时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "上次登陆Ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "本次登陆时间")
    private LocalDateTime currentLoginTime;

    @ApiModelProperty(value = "本次登陆Ip")
    private String currentLoginIp;

    @ApiModelProperty(value = "安全戳")
    private String securityStamp;

    private Set<String> roles;
    private Set<String> rolesDisplay;

}

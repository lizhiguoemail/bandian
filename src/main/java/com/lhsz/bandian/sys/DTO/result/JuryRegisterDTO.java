package com.lhsz.bandian.sys.DTO.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhusenlin
 * Date on 2020/8/20  15:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="JuryRegiste对象", description="用户")
public class JuryRegisterDTO extends BaseDTO {
    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "联系人手机")
    private String phoneNumber;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "部门类型")
    private String deptType;

    @ApiModelProperty(value = "客户端标识")
    private String clientId;
}

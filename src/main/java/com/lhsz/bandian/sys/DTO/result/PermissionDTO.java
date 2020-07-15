package com.lhsz.bandian.sys.DTO.result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Permission对象", description="权限")
public class PermissionDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色标识")
    private String roleId;

    @ApiModelProperty(value = "资源标识")
    private String resourceId;

    @ApiModelProperty(value = "拒绝")
    private Boolean isDeny;

    @ApiModelProperty(value = "签名")
    private String sign;


}

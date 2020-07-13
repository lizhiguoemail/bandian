package com.lhsz.bandian.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.lhsz.bandian.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("sys_permission")
@ApiModel(value="Permission对象", description="权限")
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限标识")
    @TableId(value = "permission_id", type = IdType.UUID)
    private String permissionId;

    @ApiModelProperty(value = "角色标识")
    private String roleId;

    @ApiModelProperty(value = "资源标识")
    private String resourceId;

    @ApiModelProperty(value = "拒绝")
    private Boolean isDeny;

    @ApiModelProperty(value = "签名")
    private String sign;


}

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
 * 用户角色
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user_role")
@ApiModel(value="UserRole对象", description="用户角色")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色标识")
    @TableId(value = "role_id", type = IdType.ID_WORKER)
    private Long roleId;

    @ApiModelProperty(value = "用户标识")
    private Long userId;


}
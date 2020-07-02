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
 * 应用程序
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_application")
@ApiModel(value="Application对象", description="应用程序")
public class Application extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用程序标识")
    @TableId(value = "application_id", type = IdType.ID_WORKER)
    private Long applicationId;

    @ApiModelProperty(value = "应用程序编码")
    private String code;

    @ApiModelProperty(value = "应用程序名称")
    private String name;

    @ApiModelProperty(value = "启用")
    private Boolean enabled;

    @ApiModelProperty(value = "启用注册")
    private Boolean registerEnabled;

    @ApiModelProperty(value = "扩展")
    private String extend;


}

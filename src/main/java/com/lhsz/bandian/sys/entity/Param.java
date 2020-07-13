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
 * 系统参数
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_param")
@ApiModel(value="Param对象", description="系统参数")
public class Param extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数标识")
    @TableId(value = "param_id", type = IdType.UUID)
    private String paramId;

    @ApiModelProperty(value = "参数名称")
    private String paramName;

    @ApiModelProperty(value = "参数标题")
    private String paramTitle;

    @ApiModelProperty(value = "参数内容")
    private String paramValue;

    @ApiModelProperty(value = "参数类型")
    private Integer paramType;

    @ApiModelProperty(value = "排序")
    private Integer sortId;

    @ApiModelProperty(value = "是否可编辑")
    private Boolean isEdit;


}

package com.lhsz.bandian.jt.entity;

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
 * 申报职称
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_declare_title")
@ApiModel(value="DeclareTitle对象", description="申报职称")
public class DeclareTitle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "职称标识")
    @TableId(value = "title_id", type = IdType.ASSIGN_UUID)
    private String titleId;

    @ApiModelProperty(value = "类别标识")
    private String categoryId;

    @ApiModelProperty(value = "职称编码")
    private String code;

    @ApiModelProperty(value = "职称名称")
    private String name;

    @ApiModelProperty(value = "拼音简码")
    private String pinYin;

    @ApiModelProperty(value = "流程标识")
    private String flowId;

    @ApiModelProperty(value = "流程名称")
    private String flowName;

    @ApiModelProperty(value = "排序号")
    private Integer sortId;

    @ApiModelProperty(value = "启用")
    private Boolean enabled;


}

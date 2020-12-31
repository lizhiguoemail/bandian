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
 * 申报专业
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_declare_specialty")
@ApiModel(value="DeclareSpecialty对象", description="申报专业")
public class DeclareSpecialty extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "专业标识")
    @TableId(value = "specialty_id", type = IdType.ASSIGN_UUID)
    private String specialtyId;

    @ApiModelProperty(value = "类别标识")
    private String categoryId;

    @ApiModelProperty(value = "专业编码")
    private String code;

    @ApiModelProperty(value = "专业名称")
    private String name;

    @ApiModelProperty(value = "拼音简码")
    private String pinYin;

    @ApiModelProperty(value = "排序号")
    private Integer sortId;

    @ApiModelProperty(value = "启用")
    private Boolean enabled;


}

package com.lhsz.bandian.jt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评委会申报计划
 * </p>
 *
 * @author zhusenlin
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_jury_declare_plan")
@ApiModel(value="JtJuryDeclarePlan对象", description="评委会申报计划")
public class JuryDeclarePlan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "计划标识")
    @TableId(value = "plan_id", type = IdType.ASSIGN_UUID)
    private String planId;

    @ApiModelProperty(value = "部门标识")
    private String deptId;

    @ApiModelProperty(value = "申报系列 ( jt_declare_category )")
    private String declareCategory;

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialty;

    @ApiModelProperty(value = "申报职称 ( jt_declare_title )")
    private String declareTitle;

    @ApiModelProperty(value = "年度")
    private String declareYear;

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "开始时间")
    private String beginDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @ApiModelProperty(value = "联系人")
    private String contactName;

    @ApiModelProperty(value = "联系人电话")
    private String contactTelephone;

    @ApiModelProperty(value = "评审结果")
    private String reviewResult;

    @ApiModelProperty(value = "评审范围")
    private String reviewRange;

    @ApiModelProperty(value = "评审方案")
    private String reviewPlanAttach;

    @ApiModelProperty(value = "其他相关附件")
    private String relevantAttach;

    @ApiModelProperty(value = "计划说明")
    private String planDescription;


}

package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author Zhangrx
 * @Date 2020/7/21 9:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_edu")
@ApiModel(value="Edu对象", description="教育经历")
public class JtEduDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教育标识")
    @TableId(value = "edu_id", type = IdType.ASSIGN_UUID)
    private String eduId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "开始日期")
    private String beginDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "学历 ( edu-type )")
    private String eduType;

    @ApiModelProperty(value = "学历编号")
    private String eduNo;

    @ApiModelProperty(value = "毕业学校")
    private String gradSchool;

    @ApiModelProperty(value = "学制")
    private Integer eduYears;

    @ApiModelProperty(value = "专业")
    private String specialty;

    @ApiModelProperty(value = "毕业时间")
    private String gradTime;

    @ApiModelProperty(value = "学位 ( edu_degree )")
    private String degreeName;

    @ApiModelProperty(value = "学位编号")
    private String degreeNo;

    @ApiModelProperty(value = "是否海外经历( sys-yesno )")
    private String isOversea;

    @ApiModelProperty(value = "审批单位标识")
    private String chkOfficeId;

    @ApiModelProperty(value = "审批单位名称")
    private String chkOfficeName;

    @ApiModelProperty(value = "审核人标识")
    private String chkUserId;

    @ApiModelProperty(value = "审核人名称")
    private String chkUserName;

    @ApiModelProperty(value = "审核状态")
    private String chkStatus;

    @ApiModelProperty(value = "审核意见")
    private String chkReason;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime chkTime;

    @ApiModelProperty(value = "学历名称")
    private String eduTypeName;

    @ApiModelProperty(value = "学位名称")
    private String eduDegreeName;

    @ApiModelProperty(value = "海外经历名称")
    private String sysYesnoName;
}

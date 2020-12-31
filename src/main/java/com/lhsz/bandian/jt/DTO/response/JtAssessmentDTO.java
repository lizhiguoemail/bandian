package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.jt.entity.Assessment;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author Zhangrx
 * @Date 2020/7/20 15:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="JtAssessment对象", description="任职考核")
public class JtAssessmentDTO extends BaseDTO {

    public JtAssessmentDTO (){}

    public JtAssessmentDTO (Assessment assessment){
        CopyUtils.copyProperties(assessment,this);
        this.setId(assessment.getAssessmentId());
    }

    @ApiModelProperty(value = "考核标识")
    @TableId(value = "assessmend_id",type = IdType.ASSIGN_UUID)
    private String assessmentId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "考核年度")
    private String assessmentYear;

    @ApiModelProperty(value = "考核结果")
    private  String assessmentResult;

    @ApiModelProperty(value = "考核意见")
    private String assessmentReason;

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

    @ApiModelProperty(value = "考核结果名称")
    private String assessmentResultName;
}

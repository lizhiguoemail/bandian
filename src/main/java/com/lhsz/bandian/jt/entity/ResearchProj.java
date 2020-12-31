package com.lhsz.bandian.jt.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.entity.BaseEntity;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 主持参与科研项目
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_research_proj")
@ApiModel(value="ResearchProj对象", description="主持参与科研项目")
public class ResearchProj extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目标识")
    @TableId(value = "project_id", type = IdType.ASSIGN_UUID)
    private String projectId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "开始日期")
    private String beginDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "项目(基金)名称")
    private String projectName;

    @ApiModelProperty(value = "来源(委托单位)")
    private String requester;

    @ApiModelProperty(value = "项目类型( research-project-type )")
    private String projectType;

    @ApiModelProperty(value = "金额(单位：万元)")
    private BigDecimal amount;

    @ApiModelProperty(value = "参与人数")
    private Integer partCount;

    @ApiModelProperty(value = "排行")
    private Integer rank;

    @ApiModelProperty(value = "主要任务")
    private String mainTask;

    @ApiModelProperty(value = "项目介绍")
    private String projectIntro;

    @ApiModelProperty(value = "是否结题( sys-yesno )")
    private String isFinshed;

    @ApiModelProperty(value = "是否主要项目( sys-yesno )")
    private String isPrimary;

    @ApiModelProperty(value = "级别( jt_awards_type )")
    private String researchLevel;

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


}

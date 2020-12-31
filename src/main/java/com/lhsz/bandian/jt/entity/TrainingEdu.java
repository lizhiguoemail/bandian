package com.lhsz.bandian.jt.entity;

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
 * 继续教育
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_training_edu")
@ApiModel(value="TrainingEdu对象", description="继续教育")
public class TrainingEdu extends BaseEntity {

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

    @ApiModelProperty(value = "培训项目")
    private String trainingItem;

    @ApiModelProperty(value = "培训学校")
    private String trainingSchool;

    @ApiModelProperty(value = "组织单位")
    private String organCorp;

    @ApiModelProperty(value = "学习情况")
    private String learnDescription;

    @ApiModelProperty(value = "课程类型( course-type )")
    private String courseType;

    @ApiModelProperty(value = "学时(单位:时)")
    private Integer trainingHours;

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

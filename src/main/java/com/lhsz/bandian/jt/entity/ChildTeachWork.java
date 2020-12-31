package com.lhsz.bandian.jt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.entity.BaseEntity;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中小学幼儿老师日常教学
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_child_teach_work")
@ApiModel(value="ChildTeachWork对象", description="中小学幼儿老师日常教学")
public class ChildTeachWork extends BaseEntity {



    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教学标识")
    @TableId(value = "teach_id", type = IdType.ASSIGN_UUID)
    private String teachId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "授课学年")
    private String schoolYear;

    @ApiModelProperty(value = "课程性质( course-type )")
    private String courseType;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "周课时")
    private Integer weekHours;

    @ApiModelProperty(value = "学年总课时")
    private Integer yearHours;

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

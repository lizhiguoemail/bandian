package com.lhsz.bandian.jt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 党校教学工作
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_party_teach_work")
@ApiModel(value="PartyTeachWork对象", description="党校教学工作")
public class PartyTeachWork extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教学标识")
    @TableId(value = "teach_id", type = IdType.ASSIGN_UUID)
    private String teachId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "学年年份")
    private String schoolYear;

    @ApiModelProperty(value = "学年总课时")
    private Integer totalHours;

    @ApiModelProperty(value = "主题班次专题课次数")
    private Integer specialCourses;

    @ApiModelProperty(value = "主题班次专题课平均优秀率")
    private Integer specialAvgExcellentRate;

    @ApiModelProperty(value = "其他课现场教学")
    private Integer otherTeach;

    @ApiModelProperty(value = "其他课堂等")
    private Integer otherClass;

    @ApiModelProperty(value = "其他课平均优秀率")
    private Integer otherAvgExcellentRate;

    @ApiModelProperty(value = "研究生班次数")
    private Integer graduateCourses;

    @ApiModelProperty(value = "研究生班平均优秀率")
    private Integer graduateAvgExcellentRate;

    @ApiModelProperty(value = "课程年平均优秀率")
    private Integer yearAvgExcellentRate;

    @ApiModelProperty(value = "年度优秀课率")
    private Integer yearExcellentRate;

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

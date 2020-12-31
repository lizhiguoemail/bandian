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
 * 获奖情况
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_awards")
@ApiModel(value="Awards对象", description="获奖情况")
public class Awards extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "奖项标识")
    @TableId(value = "awards_id", type = IdType.ASSIGN_UUID)
    private String awardsId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "获奖项目名称")
    private String awardsItemName;

    @ApiModelProperty(value = "奖励级别( jt_awards_type )")
    private String awardsType;

    @ApiModelProperty(value = "获奖名称( jt_awards_type )")
    private String awardsName;

    @ApiModelProperty(value = "获奖等级( awards-level )")
    private String awardsLevel;

    @ApiModelProperty(value = "其它获奖名称")
    private String otherAwardsName;

    @ApiModelProperty(value = "是否主要奖项( sys-yesno )")
    private String isPrimary;

    @ApiModelProperty(value = "排名")
    private Integer rank;

    @ApiModelProperty(value = "参与人数")
    private Integer partCount;

    @ApiModelProperty(value = "获奖时间")
    private String awardsDate;

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

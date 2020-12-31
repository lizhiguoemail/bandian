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
 * 专利(著作权)
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_patent")
@ApiModel(value="Patent对象", description="专利(著作权)")
public class Patent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "专利标识")
    @TableId(value = "patent_id", type = IdType.ASSIGN_UUID)
    private String patentId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "专利号")
    private String patentNo;

    @ApiModelProperty(value = "专利名称")
    private String patentName;

    @ApiModelProperty(value = "专利类型( patent-type )")
    private String patentType;

    @ApiModelProperty(value = "批准时间")
    private String approvedDate;

    @ApiModelProperty(value = "排名")
    private Integer rank;

    @ApiModelProperty(value = "参与人数")
    private Integer partCount;

    @ApiModelProperty(value = "是否授权( sys-yesno )")
    private String isAuthorization;

    @ApiModelProperty(value = "是否投产( sys-yesno )")
    private String isProduction;

    @ApiModelProperty(value = "发明(设计)人")
    private String inventorUser;

    @ApiModelProperty(value = "申请地区")
    private String applyArea;

    @ApiModelProperty(value = "专利摘要")
    private String patentSummary;

    @ApiModelProperty(value = "申请人")
    private String applyUser;

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

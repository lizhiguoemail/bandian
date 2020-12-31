package com.lhsz.bandian.jt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.entity.BaseEntity;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 论文
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_thesis")
@ApiModel(value="Thesis对象", description="论文")
public class Thesis extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "论文标识")
    @TableId(value = "thesis_id", type = IdType.ASSIGN_UUID)
    private String thesisId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "论文题目")
    private String thesisName;

    @ApiModelProperty(value = "刊物名称")
    private String journalsName;

    @ApiModelProperty(value = "论文类别( thesis-type )")
    private String thesisType;

    @ApiModelProperty(value = "发表年份")
    private String publishYear;

    @ApiModelProperty(value = "是否代表论文( sys-yesno )")
    private String isRepresentative;

    @ApiModelProperty(value = "是否通讯作者( sys-yesno )")
    private String isAuthor;

    @ApiModelProperty(value = "排名")
    private Integer rank;

    @ApiModelProperty(value = "参与人数")
    private Integer partCount;

    @ApiModelProperty(value = "索引情况(index-type)")
    private String indexType;

    @ApiModelProperty(value = "影响因子（请填写数字,没有请填0）")
    private Integer impactFactors;

    @ApiModelProperty(value = "被引用次数（单位：次）")
    private Integer referencesCount;

    @ApiModelProperty(value = "摘要")
    private String summary;

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

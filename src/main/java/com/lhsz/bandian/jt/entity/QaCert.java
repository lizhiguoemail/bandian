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
 * 资质证书
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_qa_cert")
@ApiModel(value="QaCert对象", description="资质证书")
public class QaCert extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "证书标识")
    @TableId(value = "cert_id", type = IdType.ASSIGN_UUID)
    private String certId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "获得时间")
    private String beginDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @ApiModelProperty(value = "证书名称")
    private String certName;

    @ApiModelProperty(value = "专业名称")
    private String specialty;

    @ApiModelProperty(value = "证书等级")
    private String certLevel;

    @ApiModelProperty(value = "证书编号")
    private String certNo;

    @ApiModelProperty(value = "发证机构")
    private String certOrgan;

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

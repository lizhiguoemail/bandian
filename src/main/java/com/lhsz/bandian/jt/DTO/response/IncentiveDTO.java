package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.Incentive;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author zhusenlin
 * Date on 2020/7/22  14:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_incentive")
@ApiModel(value="Incentive对象", description="奖惩情况")
public class IncentiveDTO extends BaseDTO {
    public IncentiveDTO(Incentive incentive){
        CopyUtils.copyProperties(incentive, this);
        this.setId(incentive.getIncentiveId());
    }
    public IncentiveDTO(){
    }
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "奖惩标识")
    @TableId(value = "incentive_id", type = IdType.ASSIGN_UUID)
    private String incentiveId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "奖惩名称")
    private String incentiveName;

    @ApiModelProperty(value = "奖惩类型( incentive-type )")
    private String incentiveType;

    @ApiModelProperty(value = "奖惩时间")
    private String incentiveDate;

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

    @ApiModelProperty(value = "奖惩类型名称")
    private String incentiveTypeName;

}

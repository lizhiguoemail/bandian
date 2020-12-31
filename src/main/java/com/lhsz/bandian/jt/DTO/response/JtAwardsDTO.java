package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.jt.entity.Awards;
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
 * @Date 2020/7/20 16:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="JtAwards对象", description="获奖情况")
public class JtAwardsDTO extends BaseDTO {

    public JtAwardsDTO(){}

    public JtAwardsDTO(Awards awards){
        CopyUtils.copyProperties(awards,this);
        this.setId(awards.getAwardsId());
    }

    @ApiModelProperty(value = "奖项标识")
    @TableId(value = "awards_id",type = IdType.ASSIGN_UUID)
    private String awardsId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "获奖项目名称")
    private String awardsItemName;

    @ApiModelProperty(value = "奖励级别")
    private String awardsType;

    @ApiModelProperty(value = "获奖名称")
    private String awardsName;

    @ApiModelProperty(value = "获奖等级")
    private String awardsLevel;

    @ApiModelProperty(value = "其他获奖名称")
    private String otherAwardsName;

    @ApiModelProperty(value = "是否主要奖项")
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

    @ApiModelProperty(value = "奖励级别名称")
    private String awardsTypeName;

    @ApiModelProperty(value = "获奖名称字典名称")
    private String jtAwardsName;

    @ApiModelProperty(value = "获奖等级名称")
    private String awardsLevelName;

    @ApiModelProperty(value = "主要奖项名称")
    private String sysYesnoName;

}

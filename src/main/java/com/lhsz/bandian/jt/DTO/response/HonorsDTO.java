package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.Honors;
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
 * Date on 2020/7/22  14:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_honors")
@ApiModel(value="Honors对象", description="获得荣誉")
public class HonorsDTO extends BaseDTO {
    public HonorsDTO(Honors honors){
        CopyUtils.copyProperties(honors, this);
        this.setId(honors.getHonorId());
    }
    public HonorsDTO(){

    }
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "荣誉标识")
    @TableId(value = "honor_id", type = IdType.ASSIGN_UUID)
    private String honorId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "荣誉称号名称")
    private String honorName;

    @ApiModelProperty(value = "授予单位")
    private String grantOrgan;

    @ApiModelProperty(value = "级别( jt_awards_type )")
    private String honorType;

    @ApiModelProperty(value = "授予时间")
    private String grantDate;

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

    @ApiModelProperty(value = "级别名称")
    private String honorTypeName;

}

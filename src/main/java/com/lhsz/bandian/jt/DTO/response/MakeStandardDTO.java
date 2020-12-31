package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.Incentive;
import com.lhsz.bandian.jt.entity.MakeStandard;
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
 * Date on 2020/7/22  14:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_make_standard")
@ApiModel(value="MakeStandard对象", description="主持(参与)制定标准")

public class MakeStandardDTO extends BaseDTO {
    public MakeStandardDTO(MakeStandard makeStandard){
        CopyUtils.copyProperties(makeStandard, this);
        this.setId(makeStandard.getStandardId());
    }
    public MakeStandardDTO(){
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标准标识")
    @TableId(value = "standard_id", type = IdType.ASSIGN_UUID)
    private String standardId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "标准名称")
    private String standardName;

    @ApiModelProperty(value = "标准编号")
    private String standardNo;

    @ApiModelProperty(value = "标准级别( standardt-type )")
    private String standardType;

    @ApiModelProperty(value = "发布时间")
    private String publishedDate;

    @ApiModelProperty(value = "主持或参与( participate-type )")
    private String partType;

    @ApiModelProperty(value = "标准简介")
    private String standardSummary;

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

    @ApiModelProperty(value = "标准级别名称( standardt-type )")
    private String standardTypeName;

    @ApiModelProperty(value = "主持或参与名称( participate-type )")
    private String partTypeName;

}

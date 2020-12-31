package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.jt.entity.AcadTech;
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
 * @Date 2020/7/20 11:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="JtAcadTech对象", description="学术技术兼职")
public class JtAcadTechDTO extends BaseDTO {

    public JtAcadTechDTO (){}

    public JtAcadTechDTO (AcadTech acadTech){
        CopyUtils.copyProperties(acadTech,this);
        this.setId(acadTech.getTechId());
    }

    @ApiModelProperty(value = "技术标识")
    @TableId(value = "tech_id", type = IdType.ASSIGN_UUID)
    private String TechId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "开始日期")
    private String beginDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "单位或组织")
    private String organCorp;

    @ApiModelProperty(value = "所任职务")
    private String jobTitle;

    @ApiModelProperty(value = "职务职责")
    private String jobDescription;

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

package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.EngnTechProj;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zhusenlin
 * Date on 2020/7/20  16:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_engn_tech_proj")
@ApiModel(value="EngnTechProj对象", description="主持参与工程技术项目")
public class EngnTechProjDTO extends BaseDTO {
    public EngnTechProjDTO(EngnTechProj engnTechProj){
        CopyUtils.copyProperties(engnTechProj,this);
        this.setId(engnTechProj.getProjectId());
    }
    public EngnTechProjDTO(){

    }
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目标识")
    @TableId(value = "project_id", type = IdType.ASSIGN_UUID)
    private String projectId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "开始日期")
    private String beginDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目类别")
    private String projectType;

    @ApiModelProperty(value = "主持或参与( participate-type )")
    private String partType;

    @ApiModelProperty(value = "金额(单位:万元)")
    private BigDecimal amount;

    @ApiModelProperty(value = "项目规模")
    private String projectSize;

    @ApiModelProperty(value = "项目描述")
    private String projectIntro;

    @ApiModelProperty(value = "本人职责")
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

    @ApiModelProperty(value = "主持或参与名称")
    private String partTypeName;

}

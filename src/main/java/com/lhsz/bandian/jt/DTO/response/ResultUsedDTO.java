package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.entity.BaseEntity;
import com.lhsz.bandian.jt.entity.ResearchProj;
import com.lhsz.bandian.jt.entity.ResultUsed;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 成果被批示、采纳、运用和推广
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_result_used")
@ApiModel(value="ResultUsed对象", description="成果被批示、采纳、运用和推广")
public class ResultUsedDTO extends BaseDTO {

    public ResultUsedDTO (ResultUsed resultUsed){
        CopyUtils.copyProperties(resultUsed,this);
        this.setId(resultUsed.getResultId());
    }

    public ResultUsedDTO() {
    }

    @ApiModelProperty(value = "成果标识")
    @TableId(value = "result_id", type = IdType.ASSIGN_UUID)
    private String resultId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "产品技术名称")
    private String resultName;

    @ApiModelProperty(value = "立项时间")
    private String projectDate;

    @ApiModelProperty(value = "企业名称")
    private String corpName;

    @ApiModelProperty(value = "研发投入")
    private BigDecimal researchInvestment;

    @ApiModelProperty(value = "经济效益")
    private String economicBenefits;

    @ApiModelProperty(value = "社会效益")
    private String socialBenefits;

    @ApiModelProperty(value = "技术创新水平")
    private String techInnovation;

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

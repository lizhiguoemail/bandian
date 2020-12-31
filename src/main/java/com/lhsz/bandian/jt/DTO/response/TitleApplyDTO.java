package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.TitleApply;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 申报职称
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_title_apply")
@ApiModel(value="TitleApply对象", description="申报职称")
public class TitleApplyDTO extends BaseDTO {

    public TitleApplyDTO (TitleApply titleApply){
        if(titleApply!=null){
            CopyUtils.copyProperties(titleApply,this);
            this.setId(titleApply.getApplyId());
        }
    }

    public TitleApplyDTO() {
    }


    @ApiModelProperty(value = "申请标识")
    @TableId(value = "apply_id", type = IdType.ASSIGN_UUID)
    private String applyId;

    @ApiModelProperty(value = "计划标识")
    private String planId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "年度")
    private String applyYear;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "证件照")
    private String photo;

    @ApiModelProperty(value = "签署保证书")
    private Boolean isSignGuarantee;

    @ApiModelProperty(value = "申报系列 ( jt_declare_category )")
    private String declareCategory;

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialty;

    @ApiModelProperty(value = "申报职称 ( jt_declare_title )")
    private String declareTitle;

    @ApiModelProperty(value = "审批单位标识")
    private String chkOfficeId;

    @ApiModelProperty(value = "审批单位名称")
    private String chkOfficeName;

    @ApiModelProperty(value = "提交次数")
    private Integer submitTimes;

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

    @ApiModelProperty(value = "申报系列 ( jt_declare_category )")
    private String declareCategoryName;

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialtyName;

    @ApiModelProperty(value = "申报职称 ( jt_declare_title )")
    private String declareTitleName;


}

package com.lhsz.bandian.jt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 申报职称审核
 * </p>
 *
 * @author zhusenlin
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_title_check")
@ApiModel(value="JtTitleCheck对象", description="申报职称审核")
public class TitleCheck extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "审核标识")
    @TableId(value = "check_id", type = IdType.ASSIGN_UUID)
    private String checkId;

    @ApiModelProperty(value = "申请标识")
    private String applyId;

    @ApiModelProperty(value = "部门类型(1 主管部门,2 评委会)")
    private Integer deptType;

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

    @ApiModelProperty(value = "审核步骤")
    private Integer chkStep;

    @ApiModelProperty(value = "是否审核完成")
    private Boolean isChecked;


}

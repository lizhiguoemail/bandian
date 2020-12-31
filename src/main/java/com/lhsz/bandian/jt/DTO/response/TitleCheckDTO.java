package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lhsz.bandian.jt.entity.TitleCheck;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * @author zhusenlin
 * Date on 2020/8/13  15:19
 */
public class TitleCheckDTO extends BaseDTO {

    public TitleCheckDTO(TitleCheck titleCheck){
        CopyUtils.copyProperties(titleCheck,this);
        this.setId(titleCheck.getCheckId());
    }
    public TitleCheckDTO(){

    }

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

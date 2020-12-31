package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.entity.BaseEntity;
import com.lhsz.bandian.jt.entity.UserProfile;
import com.lhsz.bandian.jt.entity.Work;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 工作经历
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_work")
@ApiModel(value="Work对象", description="工作经历")
public class WorkDTO extends BaseDTO {

    public WorkDTO (Work work){
        CopyUtils.copyProperties(work,this);
        this.setId(work.getWorkId());
    }

    public WorkDTO() {
    }

    @ApiModelProperty(value = "工作标识")
    @TableId(value = "work_id", type = IdType.ASSIGN_UUID)
    private String workId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "开始日期")
    private String beginDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "工作单位")
    private String workCorp;

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialty1;

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialty2;

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialty3;

    @ApiModelProperty(value = "职务")
    private String jobTitle;

    @ApiModelProperty(value = "职务职责")
    private String jobDescription;

    @ApiModelProperty(value = "是否援藏援疆援青援外东西部扶贫( supper-external )")
    private String isSupperExternal;

    @ApiModelProperty(value = "是否博士后工作经历( sys-yesno )")
    private String isPostdoctoralWork;

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

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialty1Name;

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialty2Name;

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialty3Name;

    @ApiModelProperty(value = "是否援藏援疆援青援外东西部扶贫( supper-external )")
    private String isSupperExternalName;

    @ApiModelProperty(value = "是否博士后工作经历( sys-yesno )")
    private String isPostdoctoralWorkName;
}

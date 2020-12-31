package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.entity.BaseEntity;
import com.lhsz.bandian.jt.entity.ResultUsed;
import com.lhsz.bandian.jt.entity.SportCoaches;
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
 * 培养输送运动员
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_sport_coaches")
@ApiModel(value="SportCoaches对象", description="培养输送运动员")
public class SportCoachesDTO extends BaseDTO {

    public SportCoachesDTO (SportCoaches sportCoaches){
        CopyUtils.copyProperties(sportCoaches,this);
        this.setId(sportCoaches.getTrainingId());
    }

    public SportCoachesDTO() {
    }

    @ApiModelProperty(value = "培养标识")
    @TableId(value = "training_id", type = IdType.ASSIGN_UUID)
    private String trainingId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "开始日期")
    private String beginDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "运动员姓名")
    private String athletesName;

    @ApiModelProperty(value = "训练单位名称")
    private String trainingCorp;

    @ApiModelProperty(value = "是否越级输送( sys-yesno )")
    private String isHigherLevel;

    @ApiModelProperty(value = "其他需要说明的情况")
    private String trainingDescription;

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

    @ApiModelProperty(value = "是否越级输送名字")
    private String isHigherLevelName;
}

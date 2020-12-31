package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.PartyTeachTeamWork;
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
 * Date on 2020/7/22  15:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_party_teach_team_work")
@ApiModel(value="PartyTeachTeamWork对象", description="党校教师团队工作")
public class PartyTeachTeamWorkDTO extends BaseDTO {
    public PartyTeachTeamWorkDTO(PartyTeachTeamWork partyTeachTeamWork){
        CopyUtils.copyProperties(partyTeachTeamWork, this);
        this.setId(partyTeachTeamWork.getTeachId());
    }
    public PartyTeachTeamWorkDTO(){

    }
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教学标识")
    @TableId(value = "teach_id", type = IdType.ASSIGN_UUID)
    private String teachId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "开始日期")
    private String beginDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "业绩类别")
    private String category;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "本人排名或所发挥作用")
    private String rankingEffect;

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

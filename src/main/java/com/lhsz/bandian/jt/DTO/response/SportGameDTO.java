package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.entity.BaseEntity;
import com.lhsz.bandian.jt.entity.SportCoaches;
import com.lhsz.bandian.jt.entity.SportGame;
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
 * 竞技体育比赛成果
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_sport_game")
@ApiModel(value="SportGame对象", description="竞技体育比赛成果")
public class SportGameDTO extends BaseDTO {

    public SportGameDTO (SportGame sportGame){
        CopyUtils.copyProperties(sportGame,this);
        this.setId(sportGame.getGameId());
    }

    public SportGameDTO() {
    }

    @ApiModelProperty(value = "比赛标识")
    @TableId(value = "game_id", type = IdType.ASSIGN_UUID)
    private String gameId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "开始日期")
    private String beginDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "竞赛名称")
    private String sportName;

    @ApiModelProperty(value = "项目名称")
    private String gameName;

    @ApiModelProperty(value = "运动员姓名")
    private String athletesName;

    @ApiModelProperty(value = "形式( sport-game-type )")
    private String gameType;

    @ApiModelProperty(value = "比赛成绩( sport-game-result )")
    private String gameResult;

    @ApiModelProperty(value = "比赛时间")
    private String gameDate;

    @ApiModelProperty(value = "比赛地点")
    private String gameAddr;

    @ApiModelProperty(value = "其他需要说明的情况")
    private String gameDescription;

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


    @ApiModelProperty(value = "形式名字")
    private String gameTypeName;

    @ApiModelProperty(value = "比赛成绩名字")
    private String gameResultName;
}

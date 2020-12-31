package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.Writings;
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
 * 著(译)作(教材)
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_writings")
@ApiModel(value="Writings对象", description="著(译)作(教材)")
public class WritingsDTO extends BaseDTO {

    public WritingsDTO (Writings writings){
        CopyUtils.copyProperties(writings,this);
        this.setId(writings.getWritingId());
    }

    public WritingsDTO() {
    }


    @ApiModelProperty(value = "著作标识")
    @TableId(value = "writing_id", type = IdType.ASSIGN_UUID)
    private String writingId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "书名")
    private String bookName;

    @ApiModelProperty(value = "出版单位")
    private String pressName;

    @ApiModelProperty(value = "出版时间")
    private String pressDate;

    @ApiModelProperty(value = "出版物类型( press-book-type )")
    private String pressBookType;

    @ApiModelProperty(value = "ISBN")
    private String bookIsbn;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "作者排名")
    private Integer authorRank;

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

    @ApiModelProperty(value = "出版物类型( press-book-type )")
    private String pressBookTypeName;


}

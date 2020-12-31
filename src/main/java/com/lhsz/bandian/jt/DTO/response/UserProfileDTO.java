package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.UserProfile;
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
 * 基本信息
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_user_profile")
@ApiModel(value="UserProfile对象", description="基本信息")
public class UserProfileDTO extends BaseDTO {

    public UserProfileDTO (UserProfile userProfile){
        CopyUtils.copyProperties(userProfile,this);
        this.setId(userProfile.getUserId());
    }

    public UserProfileDTO() {
    }

    @ApiModelProperty(value = "用户标识")
    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    private String userId;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "曾用名")
    private String usedName;

    @ApiModelProperty(value = "性别 ( sys-gender )")
    private String gender;

    @ApiModelProperty(value = "政治面貌 ( sys-party )")
    private String party;

    @ApiModelProperty(value = "民族 ( sys-nation )")
    private String nation;

    @ApiModelProperty(value = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "证件照")
    private String certPhoto;

    @ApiModelProperty(value = "证件类型( cert-type )")
    private String certType;

    @ApiModelProperty(value = "证件号码")
    private String certNo;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "档案存放单位")
    private String filePosition;

    @ApiModelProperty(value = "单位性质 ( corp-nature )")
    private String workCorpNature;

    @ApiModelProperty(value = "参加工作时间")
    private String workDate;

    @ApiModelProperty(value = "工作单位")
    private String workPlace;

    @ApiModelProperty(value = "工作部门")
    private String workDept;

    @ApiModelProperty(value = "工作岗位")
    private String workJob;

    @ApiModelProperty(value = "工作单位地址")
    private String workAddr;

    @ApiModelProperty(value = "行政职务")
    private String jobTitle;

    @ApiModelProperty(value = "主管部门")
    private String higherOffice;

    @ApiModelProperty(value = "申报类型 ( declare-type )")
    private String declareType;

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

    @ApiModelProperty(value = "户籍所在地 ( 三级联动 )")
    private String domicile3;

    @ApiModelProperty(value = "户籍所在地 ( 三级联动 )")
    private String domicile2;

    @ApiModelProperty(value = "户籍所在地 ( 三级联动 )")
    private String domicile1;

    @ApiModelProperty(value = "性别 ( sys-gender )")
    private String genderName;

    @ApiModelProperty(value = "政治面貌 ( sys-party )")
    private String partyName;

    @ApiModelProperty(value = "民族 ( sys-nation )")
    private String nationName;

    @ApiModelProperty(value = "证件类型( cert-type )")
    private String certTypeName;

    @ApiModelProperty(value = "单位性质 ( corp-nature )")
    private String workCorpNatureName;

    @ApiModelProperty(value = "申报类型 ( declare-type )")
    private String declareTypeName;

    @ApiModelProperty(value = "申报系列 ( jt_declare_category )")
    private String declareCategoryName;

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialtyName;

    @ApiModelProperty(value = "申报职称 ( jt_declare_title )")
    private String declareTitleName;

    @ApiModelProperty(value = "主管单位名称")
    private String higherOfficeName;
}

package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.entity.BaseEntity;
import com.lhsz.bandian.jt.entity.TitleApply;
import com.lhsz.bandian.jt.entity.TitleCert;
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
 * 职称证书
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_title_cert")
@ApiModel(value="TitleCert对象", description="职称证书")
public class TitleCertDTO extends BaseDTO {

    public TitleCertDTO (TitleCert titleCert){
        CopyUtils.copyProperties(titleCert,this);
        this.setId(titleCert.getCertId());
    }

    public TitleCertDTO() {
    }

    @ApiModelProperty(value = "证书标识")
    @TableId(value = "cert_id", type = IdType.ASSIGN_UUID)
    private String certId;

    @ApiModelProperty(value = "申请标识")
    private String applyId;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "证件照")
    private String photo;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "证书编号")
    private String certNo;

    @ApiModelProperty(value = "身份证号")
    private Boolean idCardNo;

    @ApiModelProperty(value = "申报系列")
    private String declareCategory;

    @ApiModelProperty(value = "申报专业")
    private String declareSpecialty;

    @ApiModelProperty(value = "申报职称")
    private String declareTitle;

    @ApiModelProperty(value = "评委员会名称")
    private String juryName;

    @ApiModelProperty(value = "评审通过时间")
    private LocalDateTime chkPassTime;

    @ApiModelProperty(value = "发证日期")
    private String publishedDate;

    @ApiModelProperty(value = "发证单位")
    private String publishedOrgan;

    @ApiModelProperty(value = "查询码")
    private String searchCode;

    @ApiModelProperty(value = "评委会标识")
    private String officeId;

    @ApiModelProperty(value = "评委会名称")
    private String officeName;


}

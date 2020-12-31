package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.JuryDept;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhusenlin
 * Date on 2020/7/22  14:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_jury_declare_subject")
@ApiModel(value="JuryDeclareSubject对象", description="评委会申报对象资格")
public class JuryDeclareSubjectDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "对象标识")
    @TableId(value = "subject_id", type = IdType.ASSIGN_UUID)
    private String subjectId;

    @ApiModelProperty(value = "部门标识")
    private String deptId;

    @ApiModelProperty(value = "申报系列 ( jt_declare_category )")
    private String declareCategory;
    private String declareCategoryName;

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialty;
    private String declareSpecialtyName;

    @ApiModelProperty(value = "申报职称 ( jt_declare_title )")
    private String declareTitle;
    private String declareTitleName;

    private List<JuryDept> juryDeptList;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区县")
    private String  county;
}

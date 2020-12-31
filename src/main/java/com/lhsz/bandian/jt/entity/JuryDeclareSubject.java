package com.lhsz.bandian.jt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.lhsz.bandian.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 评委会申报对象资格
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_jury_declare_subject")
@ApiModel(value="JuryDeclareSubject对象", description="评委会申报对象资格")
public class JuryDeclareSubject extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "对象标识")
    @TableId(value = "subject_id", type = IdType.ASSIGN_UUID)
    private String subjectId;

    @ApiModelProperty(value = "部门标识")
    private String deptId;

    @ApiModelProperty(value = "申报系列 ( jt_declare_category )")
    private String declareCategory;

    @ApiModelProperty(value = "申报专业 ( jt_declare_specialty )")
    private String declareSpecialty;

    @ApiModelProperty(value = "申报职称 ( jt_declare_title )")
    private String declareTitle;

    private List<JuryDept> juryDeptList;


}

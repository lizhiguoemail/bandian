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

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评委会主管部门
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_jury_dept")
@ApiModel(value="JuryDept对象", description="评委会主管部门")
public class JuryDept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门标识")
    @TableId(value = "dept_id", type = IdType.ASSIGN_UUID)
    private String deptId;

    @ApiModelProperty(value = "部门类型(1 主管部门,2 评委会)")
    private Integer deptType;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "部门编码")
    private String organCode;

    @ApiModelProperty(value = "负责人")
    private String leaderName;

    @ApiModelProperty(value = "负责人电话")
    private String leaderTelephone;

    @ApiModelProperty(value = "联系人")
    private String contactName;

    @ApiModelProperty(value = "联系人电话")
    private String contactTelephone;

    @ApiModelProperty(value = "评审等级")
    private String juryLevel;

    @ApiModelProperty(value = "归口部门")
    private String highterDept;

    @ApiModelProperty(value = "组建单位")
    private String formDept;

    @ApiModelProperty(value = "办公室地址")
    private String officeAddr;

    @ApiModelProperty(value = "评审范围")
    private String juryRange;

    @ApiModelProperty(value = "管理办法")
    private String manageRule;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区县")
    private String  county;
}

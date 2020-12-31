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

import java.io.Serializable;

/**
 * <p>
 * 评委会主管部门用户
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Data
@Accessors(chain = true)
@TableName("jt_jury_dept_user")
@ApiModel(value="JuryDeptUser对象", description="评委会主管部门用户")
public class JuryDeptUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门标识")
    @TableId(value = "dept_id", type = IdType.ASSIGN_UUID)
    private String deptId;

    @ApiModelProperty(value = "用户标识")
    private String userId;


}

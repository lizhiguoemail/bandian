package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.AwardsType;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author Zhangrx
 * @Date 2020/7/21 9:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_awards_type")
@ApiModel(value="AwardsType对象", description="资历级别")
public class JtAwardsTypeDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    public JtAwardsTypeDTO(){}
    public JtAwardsTypeDTO(AwardsType awardsType){
        CopyUtils.copyProperties(awardsType,this);
        this.setId(awardsType.getTypeId());
    }


    @ApiModelProperty(value = "类别标识")
    @TableId(value = "type_id", type = IdType.ASSIGN_UUID)
    private String typeId;

    @ApiModelProperty(value = "类别编码")
    private String code;

    @ApiModelProperty(value = "类别名称")
    private String name;

    @ApiModelProperty(value = "拼音简码")
    private String pinYin;

    @ApiModelProperty(value = "父标识")
    private String parentId;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "排序号")
    private Integer sortId;

    @ApiModelProperty(value = "启用")
    private Boolean enabled;

    private boolean extend;
    private boolean leaf;

    private List<JtAwardsTypeDTO> children;
}

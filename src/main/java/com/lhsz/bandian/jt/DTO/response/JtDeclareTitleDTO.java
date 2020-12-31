package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.DeclareCategory;
import com.lhsz.bandian.jt.entity.DeclareTitle;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author Zhangrx
 * @Date 2020/7/21 9:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="DeclareTitle对象", description="申报职称")
public class JtDeclareTitleDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    public JtDeclareTitleDTO(){}
    public JtDeclareTitleDTO(DeclareTitle declareTitle){
        CopyUtils.copyProperties(declareTitle,this);
        this.setId(declareTitle.getTitleId());
    }

    @ApiModelProperty(value = "职称标识")
    @TableId(value = "title_id", type = IdType.ASSIGN_UUID)
    private String titleId;

    @ApiModelProperty(value = "类别标识")
    private String categoryId;

    @ApiModelProperty(value = "职称编码")
    private String code;

    @ApiModelProperty(value = "职称名称")
    private String name;

    @ApiModelProperty(value = "拼音简码")
    private String pinYin;

    @ApiModelProperty(value = "流程标识")
    private String flowId;

    @ApiModelProperty(value = "流程名称")
    private String flowName;

    @ApiModelProperty(value = "排序号")
    private Integer sortId;

    @ApiModelProperty(value = "启用")
    private Boolean enabled;
}

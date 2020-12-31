package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lhsz.bandian.jt.entity.DeclareCategory;
import com.lhsz.bandian.jt.entity.DeclareTitle;
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
 * @Date 2020/7/21 9:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="DeclareCategory对象", description="职称系列类别")
public class JtDeclareCategoryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    public JtDeclareCategoryDTO(){

    }
    public JtDeclareCategoryDTO(DeclareCategory declareCategory){
        CopyUtils.copyProperties(declareCategory,this);
        this.setId(declareCategory.getCategoryId());
    }

    @ApiModelProperty(value = "类别标识")
    @TableId(value = "category_id", type = IdType.ASSIGN_UUID)
    private String categoryId;

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
    private List<JtDeclareCategoryDTO> children;
    private List<DeclareTitle> titleDTOList;
}

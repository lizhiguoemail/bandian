package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.DeclareSpecialty;
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
 * @Date 2020/7/21 9:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("jt_declare_specialty")
@ApiModel(value="DeclareSpecialty对象", description="申报专业")
public class JtDeclareSpecialtyDTO extends BaseDTO {

    public JtDeclareSpecialtyDTO(){
    }
    public JtDeclareSpecialtyDTO(DeclareSpecialty obj){
        CopyUtils.copyProperties(obj,this);
        this.setId(obj.getSpecialtyId());
    }
    @ApiModelProperty(value = "专业标识")
    @TableId(value = "specialty_id", type = IdType.ASSIGN_UUID)
    private String specialtyId;

    @ApiModelProperty(value = "类别标识")
    private String categoryId;

    @ApiModelProperty(value = "专业编码")
    private String code;

    @ApiModelProperty(value = "专业名称")
    private String name;

    @ApiModelProperty(value = "拼音简码")
    private String pinYin;

    @ApiModelProperty(value = "排序号")
    private Integer sortId;

    @ApiModelProperty(value = "启用")
    private Boolean enabled=false;

}

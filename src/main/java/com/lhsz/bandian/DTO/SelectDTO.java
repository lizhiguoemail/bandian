package com.lhsz.bandian.DTO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(value="selectDTO对象", description="下拉框返回对象")
public class SelectDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    public SelectDTO(){}
    public SelectDTO(String id,String text,String value,boolean disabled){
        this.id=id;
        this.text=text;
        this.value=value;
        this.disabled=disabled;
    }
    @ApiModelProperty(value = "文本")
    private String text;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disabled;
    private String id;

}

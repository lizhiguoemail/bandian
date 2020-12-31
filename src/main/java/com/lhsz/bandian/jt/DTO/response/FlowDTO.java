package com.lhsz.bandian.jt.DTO.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhsz.bandian.jt.entity.Flow;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zhusenlin
 * Date on 2020/7/22  14:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Flow对象", description="申报流程")
public class FlowDTO extends BaseDTO {

    public FlowDTO(){}
    public FlowDTO(Flow flow){
        CopyUtils.copyProperties(flow,this);
        this.setId(flow.getFlowId());
    }
    @ApiModelProperty(value = "流程标识")
    @TableId(value = "flow_id", type = IdType.ASSIGN_UUID)
    private String flowId;

    @ApiModelProperty(value = "流程名称")
    private String flowName;

    @ApiModelProperty(value = "流程值")
    private String flowValue;

    @ApiModelProperty(value = "排序号")
    private Integer sortId;

    @ApiModelProperty(value = "启用")
    private Boolean enabled;

}

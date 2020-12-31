package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.entity.Incentive;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhusenlin
 * Date on 2020/7/28  11:13
 */
@Data
@Accessors(chain = true)
@ApiModel(value="Incentive对象", description="奖惩情况")
public class IncentiveUpdateDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private Incentive data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;

}

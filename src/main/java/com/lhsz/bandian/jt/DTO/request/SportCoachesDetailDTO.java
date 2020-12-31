package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.DTO.response.ResultUsedDTO;
import com.lhsz.bandian.jt.DTO.response.SportCoachesDTO;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhusenlin
 * Date on 2020/7/20  16:20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SportCoaches对象", description="培养输送运动员")
public class SportCoachesDetailDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private SportCoachesDTO data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;
}

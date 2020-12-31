package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.DTO.response.TrainingEduDTO;
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
@ApiModel(value="TrainingEdu对象", description="继续教育")
public class TrainingEduDetailDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private TrainingEduDTO data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;
}

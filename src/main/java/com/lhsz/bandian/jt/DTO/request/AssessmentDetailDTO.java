package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.DTO.response.JtAssessmentDTO;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author Zhangrx
 * Date on 2020/7/28 10:41
 */
@Data
@Accessors(chain = true)
@ApiModel(value="Assessment对象", description="任职考核")
public class AssessmentDetailDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private JtAssessmentDTO data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;
}

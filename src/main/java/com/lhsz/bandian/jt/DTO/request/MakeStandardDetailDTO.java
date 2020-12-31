package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.DTO.response.MakeStandardDTO;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhusenlin
 * Date on 2020/7/28  14:27
 */
@Data
@Accessors(chain = true)
@ApiModel(value="MakeStandard对象", description="主持(参与)制定标准")
public class MakeStandardDetailDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private MakeStandardDTO data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;
}

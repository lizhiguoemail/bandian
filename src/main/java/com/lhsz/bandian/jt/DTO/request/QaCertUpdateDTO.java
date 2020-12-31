package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.DTO.response.QaCertDTO;
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
@ApiModel(value="QaCert对象", description="资质证书")
public class QaCertUpdateDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private QaCertDTO data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;
}

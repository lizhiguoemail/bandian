package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.entity.EngnTechProj;
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
@ApiModel(value="EngnTechProj对象", description="主持参与工程技术项目")
public class EngnTechProjAddDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private EngnTechProj data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;
}

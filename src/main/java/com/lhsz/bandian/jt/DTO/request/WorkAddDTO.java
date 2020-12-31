package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.entity.Work;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author Zhangrx
 * @Date 2020/7/28 9:18
 */
@Data
@Accessors(chain = true)
@ApiModel(value="Work对象", description="工作经历")
public class WorkAddDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private Work data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;
}

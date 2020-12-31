package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.entity.Honors;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhusenlin
 * Date on 2020/7/28  8:49
 */
@Data
@Accessors(chain = true)
@ApiModel(value="Honors对象", description="获得荣誉")
public class HonorsUpdateDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private Honors data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;

}

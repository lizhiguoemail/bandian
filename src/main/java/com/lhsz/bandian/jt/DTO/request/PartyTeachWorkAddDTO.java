package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.entity.PartyTeachWork;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhusenlin
 * Date on 2020/7/28  16:27
 */
@Data
@Accessors(chain = true)
@ApiModel(value="PartyTeachWork对象", description="党校教学工作")
public class PartyTeachWorkAddDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private PartyTeachWork data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;

}

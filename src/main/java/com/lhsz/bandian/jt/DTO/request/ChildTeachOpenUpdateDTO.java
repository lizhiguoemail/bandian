package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.entity.AcadTech;
import com.lhsz.bandian.jt.entity.ChildTeachOpen;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author zhangrx
 * Date on 2020/7/20  16:20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ChildTeachOpen对象", description="中小学幼儿老师公开课")
public class ChildTeachOpenUpdateDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private ChildTeachOpen data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;
}

package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.entity.PartyTeachTeamWork;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhusenlin
 * Date on 2020/7/28  15:37
 */
@Data
@Accessors(chain = true)
@ApiModel(value="PartyTeachTeamWork对象", description="党校教师团队工作")
public class PartyTeachTeamWorkUpdateDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private PartyTeachTeamWork data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;

}

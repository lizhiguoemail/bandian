package com.lhsz.bandian.jt.DTO.request;

import com.lhsz.bandian.jt.DTO.response.SportCoachesDTO;
import com.lhsz.bandian.jt.DTO.response.SportGameDTO;
import com.lhsz.bandian.jt.entity.SportGame;
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
@ApiModel(value="SportGame对象", description="竞技体育比赛成果")
public class SportGameAddDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private SportGameDTO data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;
}

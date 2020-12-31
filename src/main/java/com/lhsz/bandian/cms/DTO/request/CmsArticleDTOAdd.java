package com.lhsz.bandian.cms.DTO.request;

import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhusenlin
 * Date on 2020/8/28  9:23
 */
@Data
@Accessors(chain = true)
@ApiModel(value="CmsArticleDTOAdd对象", description="添加文章内容")
public class CmsArticleDTOAdd {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据")
    private CmsArticleDTO data;

    @ApiModelProperty(value = "附件")
    private List<FileUploadDTO> files;

}

package com.lhsz.bandian.cms.DTO.query;

import com.lhsz.bandian.cms.entity.CmsArticle;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 文章管理的类
 * @author LSCHOME
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="CmsArticle对象", description="文章管理")
public class CmsArticleSearchDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    public CmsArticleSearchDTO() {
    }
    public CmsArticleSearchDTO(CmsArticle cmsArticle){
        CopyUtils.copyProperties(cmsArticle,this);
        this.setId(cmsArticle.getArticleId());
    }

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章类型")
    private String code;

    private List<CmsArticleSearchDTO> children;
}

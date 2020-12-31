package com.lhsz.bandian.cms.DTO.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhsz.bandian.cms.entity.CmsArticle;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章管理的类
 * @author LSCHOME
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="CmsArticle对象", description="文章管理")
public class CmsArticleDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    public CmsArticleDTO() {
    }
    public CmsArticleDTO(CmsArticle cmsArticle){
        CopyUtils.copyProperties(cmsArticle,this);
        this.setId(cmsArticle.getArticleId());
    }

    @ApiModelProperty(value = "文章标识")
    @TableId(value = "article_id", type = IdType.ASSIGN_UUID)
    private String articleId;

    @ApiModelProperty(value = "频道标识")
    private String channelId;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "子标题")
    private String subTitle;

    @ApiModelProperty(value = "页面标题")
    private String pageTitle;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime expiredTime;

    @ApiModelProperty(value = "来源")
    private String source;

    @ApiModelProperty(value = "是否允许评论")
    private Boolean isComment;

    @ApiModelProperty(value = "是否图片新闻")
    private Boolean isImage;

    @ApiModelProperty(value = "是否有效")
    private Boolean enabled;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "文章类型(1内部, 2外部)")
    private Integer type;

    @ApiModelProperty(value = "外部链接")
    private String url;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "标签")
    private String tags;

    @ApiModelProperty(value = "关键字")
    private String keyWord;

    @ApiModelProperty(value = "视频代码")
    private String videoCode;

    @ApiModelProperty(value = "新闻图片")
    private String photos;

    @ApiModelProperty(value = "点击量")
    private Integer clicks;

    @ApiModelProperty(value = "频道标题")
    private String channelTitle;

    @ApiModelProperty(value = "频道访问路径")
    private String channelFullUrl;

    @ApiModelProperty(value = "排序号")
    private String sortId;

    private boolean extend;
    private boolean leaf;

    private List<CmsArticleDTO> children;
}

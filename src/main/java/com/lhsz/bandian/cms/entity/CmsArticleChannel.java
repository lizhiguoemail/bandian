package com.lhsz.bandian.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 文章频道与内容
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-30
 */
@Data
@Accessors(chain = true)
@TableName("cms_article_channel")
@ApiModel(value="CmsArticleChannel对象", description="文章频道与内容")
public class CmsArticleChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "频道标识")
    @TableId(value = "channel_id", type = IdType.ASSIGN_UUID)
    private String channelId;

    @ApiModelProperty(value = "文章标识")
    private String articleId;


}

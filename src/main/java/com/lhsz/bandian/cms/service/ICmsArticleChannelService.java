package com.lhsz.bandian.cms.service;

import com.lhsz.bandian.cms.entity.CmsArticleChannel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文章频道与内容 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-30
 */
public interface ICmsArticleChannelService extends IService<CmsArticleChannel> {
    List<CmsArticleChannel> getCmsArticleChannelId (String id);
}

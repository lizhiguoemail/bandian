package com.lhsz.bandian.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.cms.entity.CmsArticleChannel;
import com.lhsz.bandian.cms.mapper.CmsArticleChannelMapper;
import com.lhsz.bandian.cms.service.ICmsArticleChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文章频道与内容 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-30
 */
@Service
public class CmsArticleChannelServiceImpl extends ServiceImpl<CmsArticleChannelMapper, CmsArticleChannel> implements ICmsArticleChannelService {


    @Override
    public List<CmsArticleChannel> getCmsArticleChannelId(String id) {
        QueryWrapper<CmsArticleChannel> queryWrapper=new QueryWrapper();
        if(id!=null&&!"".equals(id))
        {
            queryWrapper.like("channel_id",id);
        }
        List<CmsArticleChannel> list1 = list(queryWrapper);
        return list1;
    }
}

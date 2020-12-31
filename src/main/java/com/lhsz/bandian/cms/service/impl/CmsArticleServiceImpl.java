package com.lhsz.bandian.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTO;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTOAdd;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTODetail;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTOUpdate;
import com.lhsz.bandian.cms.entity.CmsArticle;
import com.lhsz.bandian.cms.entity.CmsArticleChannel;
import com.lhsz.bandian.cms.mapper.CmsArticleMapper;
import com.lhsz.bandian.cms.service.ICmsArticleChannelService;
import com.lhsz.bandian.cms.service.ICmsArticleService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.service.IAttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章内容 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-30
 */
@Slf4j
@Service
public class CmsArticleServiceImpl extends ServiceImpl<CmsArticleMapper, CmsArticle> implements ICmsArticleService {

    private final CmsArticleMapper cmsArticleMapper;
    private final ICmsArticleChannelService iCmsArticleChannelService;
    private final IAttachService attachService;

    @Autowired
    public CmsArticleServiceImpl(CmsArticleMapper cmsArticleMapper, ICmsArticleChannelService iCmsArticleChannelService, IAttachService attachService) {
        this.cmsArticleMapper = cmsArticleMapper;
        this.iCmsArticleChannelService = iCmsArticleChannelService;
        this.attachService = attachService;
    }

    @Override
    public List<CmsArticleDTO> selectList(CmsArticle cmsArticle) {
        ArrayList<CmsArticleDTO> cmsArticleDTOS = new ArrayList<>();
        QueryWrapper<CmsArticle> cmsArticleQueryWrapper = new QueryWrapper<>();
        if (cmsArticle.getTitle() != null && !"".equals(cmsArticle.getTitle().trim())) {
            cmsArticleQueryWrapper.like("title", cmsArticle.getTitle());
        }
        if (cmsArticle.getType() != null && !"".equals(cmsArticle.getType())) {
            cmsArticleQueryWrapper.like("type", cmsArticle.getType());
        }
        if (cmsArticle.getEnabled() != null && !"".equals(cmsArticle.getEnabled())) {
            cmsArticleQueryWrapper.eq("enabled", cmsArticle.getEnabled());
        }
        if (cmsArticle.getSource() != null && !"".equals(cmsArticle.getSource())) {
            cmsArticleQueryWrapper.like("source", cmsArticle.getSource());
        }

        List<CmsArticle> list = cmsArticleMapper.selectList(cmsArticleQueryWrapper);

        for (CmsArticle titleApply1 : list) {
            CmsArticleDTO cmsArticleDTO = new CmsArticleDTO(titleApply1);
            cmsArticleDTOS.add(cmsArticleDTO);
        }
        return cmsArticleDTOS;
    }

    @Override
    public CmsArticleDTODetail getCmsArticle(String id) {
        CmsArticleDTODetail result = new CmsArticleDTODetail();
        CmsArticle declare = baseMapper.selectById(id);
        CmsArticleDTO cmsArticleDTO = new CmsArticleDTO();
        BeanUtils.copyProperties(declare, cmsArticleDTO);
        cmsArticleDTO.setId(declare.getChannelId());
        result.setData(cmsArticleDTO);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(cmsArticleDTO.getArticleId());
        result.setFiles(fileUploadDTOS);
        return result;
    }


    @Override
    public List<CmsArticleDTO> selectCmsArticleType(Integer type) {
        ArrayList<CmsArticleDTO> cmsArticleDTOS = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        if (type != null && !"".equals(type)) {
            queryWrapper.like("type", type);
        }
        List<CmsArticle> list = cmsArticleMapper.selectList(queryWrapper);
        for (CmsArticle typeApply1 : list) {
            CmsArticleDTO cmsArticleDTO = new CmsArticleDTO(typeApply1);
            cmsArticleDTOS.add(cmsArticleDTO);
        }
        return cmsArticleDTOS;
    }

    @Override
    public CmsArticle selectByArticleId(String articleId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (articleId != null && !"".equals(articleId)) {
            queryWrapper.like("article_id", articleId);
        }
        CmsArticle list = cmsArticleMapper.selectById(articleId);
        return list;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addCmsChannel(CmsArticleDTOAdd cmsArticleDTO) {
//        CmsArticle declare = new CmsArticle();
//        String id= UUID.randomUUID().toString();
//        cmsArticleDTO.setArticleId(id);
//        BeanUtils.copyProperties(cmsArticleDTO,declare);
//        cmsArticleDTO.setId(declare.getArticleId());
//        declare.setStatus(1);
        CmsArticle article = new CmsArticle();
        BeanUtils.copyProperties(cmsArticleDTO.getData(), article);
        article.setStatus(1);
        article.setClicks(0);
        save(article);
        CmsArticleChannel articleChannel = new CmsArticleChannel();
        articleChannel.setArticleId(article.getArticleId());
        articleChannel.setChannelId(cmsArticleDTO.getData().getChannelId());
        iCmsArticleChannelService.save(articleChannel);
        if (cmsArticleDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            cmsArticleDTO.getFiles().forEach(obj -> {
                obj.setObjectId(article.getArticleId());
                obj.setObjectType("文章附件");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }

    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateCmsChannel(CmsArticleDTOUpdate cmsArticleDTO) {
        //先把原来是关联数据删除
        QueryWrapper<CmsArticleChannel> qw = new QueryWrapper<>();
        qw.eq("article_id", cmsArticleDTO.getData().getArticleId());
        iCmsArticleChannelService.remove(qw);
        CmsArticle declare = new CmsArticle();
        BeanUtils.copyProperties(cmsArticleDTO.getData(), declare);
        updateById(declare);
        //新增更改之后的关联数据
        CmsArticleChannel declare3 = new CmsArticleChannel();
        declare3.setArticleId(cmsArticleDTO.getData().getArticleId());
        declare3.setChannelId(cmsArticleDTO.getData().getChannelId());
        iCmsArticleChannelService.save(declare3);

        if (cmsArticleDTO.getFiles().size() > 0 && !cmsArticleDTO.getFiles().isEmpty()) {
            List<Attach> attachs = new ArrayList<>();
            cmsArticleDTO.getFiles().forEach(obj -> {
                obj.setObjectId(cmsArticleDTO.getData().getArticleId());
                obj.setObjectType("文章附件");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }

    }

    @Override
    public int delCmsArticleDTO(String id) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("article_id", id);
        iCmsArticleChannelService.remove(qw);
        return baseMapper.deleteById(id);
    }

    @Override
    public boolean updateArticleClicks(String id) {
        CmsArticle article = selectByArticleId(id);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("article_id", id);
        updateWrapper.set("clicks", (article.getClicks() == null ? 0 : article.getClicks()) + 1);
        return update(article, updateWrapper);
    }

}

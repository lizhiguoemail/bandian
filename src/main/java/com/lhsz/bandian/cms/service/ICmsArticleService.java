package com.lhsz.bandian.cms.service;

import com.lhsz.bandian.cms.DTO.request.CmsArticleDTO;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTOAdd;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTODetail;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTOUpdate;
import com.lhsz.bandian.cms.entity.CmsArticle;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * <p>
 * 文章内容 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-30
 */
public interface ICmsArticleService extends IService<CmsArticle> {
    /**
     * 查询文章列表
     * @param
     * @return
     */
    List<CmsArticleDTO> selectList (CmsArticle cmsArticle);

    /**
     * 根据id进行文章查询
     * @param id
     * @return
     */
    CmsArticleDTODetail getCmsArticle (String id);

    /**
     * 根据类型进行频道查询
     * @param
     * @return
     */
    List<CmsArticleDTO> selectCmsArticleType(Integer type);

    /**
     * 根据文章编码查询文章
     * @param
     * @return
     */
    CmsArticle selectByArticleId(String articleId);

    /**
     * 添加文章
     * @param  cmsArticleDTO
     * @return
     */
    void addCmsChannel (CmsArticleDTOAdd cmsArticleDTO);

    /**
     * 更新文章
     * @param cmsArticleDTO
     * @return
     */
    void updateCmsChannel (CmsArticleDTOUpdate cmsArticleDTO);

    /**
     * 删除单篇文章
     * @param id
     * @return
     */
    int delCmsArticleDTO (String id);

    /**
     * 批量频道内容
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
//     int deleteCmsChannelByIds(String ids) throws Exception;

    /**
     * 更新文章点击量
     * @param id 文章标识
     */
    boolean updateArticleClicks(String id);
}

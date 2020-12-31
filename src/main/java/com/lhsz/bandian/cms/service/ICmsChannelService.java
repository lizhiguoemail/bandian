package com.lhsz.bandian.cms.service;

import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.cms.DTO.query.QuerydeclareCategoryDTO;
import com.lhsz.bandian.cms.DTO.request.CmsChannelDTO;
import com.lhsz.bandian.cms.entity.CmsChannel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;

import java.util.List;

/**
 * <p>
 * 频道管理 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-30
 */
public interface ICmsChannelService extends IService<CmsChannel> {
    /**
     * 查询频道列表
     * @param
     * @return
     */
    List<CmsChannelDTO> selectList (QuerydeclareCategoryDTO querydeclareCategoryDTO);

    /**
     * 根据id进行频道查询
     * @param id
     * @return
     */
    CmsChannelDTO getCmsChannel (String id);

    /**
     * 根据code进行频道查询
     * @param code
     * @return
     */
    CmsChannel getCmsChannelCode (String code);

    /**
     * 查询频道树结构
     * @return
     */
    TreeDTO getTreeDTO();

    /**
     * 添加频道管理
     * @param  cmsChannelDTO
     * @return
     */
    void addCmsChannel (CmsChannelDTO cmsChannelDTO);

    /**
     * 更新频道管理
     * @param cmsChannelDTO
     * @return
     */
    void updateCmsChannel (CmsChannelDTO cmsChannelDTO);

    /**
     * 删除单个频道
     * @param id
     * @return
     */
    int delCmsChannelDTO (String id);

    /**
     * 批量频道内容
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
//     int deleteCmsChannelByIds(String ids) throws Exception;

    /**
     * 根据parentId查询子集
     * @param parentId
     * @return
     */
    List<SelectDTO> listAllByParentId(String parentId);
}

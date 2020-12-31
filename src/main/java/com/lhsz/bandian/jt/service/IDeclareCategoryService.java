package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.jt.DTO.query.QuerydeclareCategoryDTO;
import com.lhsz.bandian.jt.DTO.response.JtDeclareCategoryDTO;
import com.lhsz.bandian.jt.entity.DeclareCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;

import java.util.List;

/**
 * <p>
 * 职称系列类别 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IDeclareCategoryService extends IService<DeclareCategory> {


    /**
     * 查询职称系列类别
     * @param querydeclareCategoryDTO
     * @return
     */
    List<JtDeclareCategoryDTO> listDeclareCategory (QuerydeclareCategoryDTO querydeclareCategoryDTO);

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    JtDeclareCategoryDTO getDeclareCategory (String id);

    /**
     * 添加职称系列类别
     * @param jtDeclareCategoryDTO
     * @return
     */
    void addDeclareCategory (JtDeclareCategoryDTO jtDeclareCategoryDTO);

    /**
     * 更新职称系列类别
     * @param jtDeclareCategoryDTO
     * @return
     */
    void updateDeclareCategory (JtDeclareCategoryDTO jtDeclareCategoryDTO);

    /**
     * 删除职称系列类别
     * @param id
     * @return
     */
    int delDeclareCategory (String id);

    TreeDTO getTreeDTO();
    List<JtDeclareCategoryDTO> getTree();


    /**
     * 根据parentId查询子集
     * @param parentId
     * @return
     */
    List<SelectDTO> listAllByParentId(String parentId);
}

package com.lhsz.bandian.jt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.jt.DTO.response.JtDeclareTitleDTO;
import com.lhsz.bandian.jt.entity.DeclareTitle;

import java.util.List;

/**
 * <p>
 * 申报职称 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IDeclareTitleService extends IService<DeclareTitle> {

    /**
     * 查询申报职称
     * @param declareTitle
     * @return
     */
    List<JtDeclareTitleDTO> listDeclareTitle (DeclareTitle declareTitle);

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    JtDeclareTitleDTO getDeclareTitle (String id);

    /**
     * 添加申报职称
     * @param jtDeclareTitleDTO
     * @return
     */
    void addDeclareTitle (JtDeclareTitleDTO jtDeclareTitleDTO);

    /**
     * 更新申报职称
     * @param jtDeclareTitleDTO
     * @return
     */
    void updateDeclareTitle (JtDeclareTitleDTO jtDeclareTitleDTO);

    /**
     * 删除申报职称
     * @param id
     * @return
     */
    int delDeclareTitle (String id);

    List<SelectDTO> listAllByCategoryId(String categoryId);

    DeclareTitle getFlowIdByCode(String code);
}

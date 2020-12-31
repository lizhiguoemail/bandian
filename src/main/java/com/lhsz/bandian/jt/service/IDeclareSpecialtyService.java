package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.jt.DTO.response.JtDeclareSpecialtyDTO;
import com.lhsz.bandian.jt.DTO.response.JtDeclareTitleDTO;
import com.lhsz.bandian.jt.entity.DeclareSpecialty;
import com.lhsz.bandian.jt.entity.DeclareSpecialty;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 申报专业 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IDeclareSpecialtyService extends IService<DeclareSpecialty> {


    /**
     * 查询申报专业
     * @param declareSpecialty
     * @return
     */
    List<JtDeclareSpecialtyDTO> listDeclareSpecialty (DeclareSpecialty declareSpecialty);

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    JtDeclareSpecialtyDTO getDeclareSpecialty (String id);

    /**
     * 添加申报专业
     * @param jtDeclareSpecialtyDTO
     * @return
     */
    void addDeclareSpecialty (JtDeclareSpecialtyDTO jtDeclareSpecialtyDTO);

    /**
     * 更新申报专业
     * @param jtDeclareSpecialtyDTO
     * @return
     */
    void updateDeclareSpecialty (JtDeclareSpecialtyDTO jtDeclareSpecialtyDTO);

    /**
     * 删除申报专业
     * @param id
     * @return
     */
    int delDeclareSpecialty (String id);

    List<SelectDTO> listAllByCategoryId(String categoryId);
}

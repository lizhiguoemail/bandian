package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.AcadTechAddDTO;
import com.lhsz.bandian.jt.DTO.request.AcadTechDetailDTO;
import com.lhsz.bandian.jt.DTO.request.AcadTechUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtAcadTechDTO;
import com.lhsz.bandian.jt.entity.AcadTech;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学术技术兼职 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IAcadTechService extends IService<AcadTech> {
    /**
     *  根据acadTech对象查循
     * @param acadTech
     * @return
     */
    List<JtAcadTechDTO> listAcadTech (AcadTech acadTech);

    /**
     * 根据用户id进行查询
     * @param techId
     * @return
     */
    JtAcadTechDTO getAcadTechId (String techId);

    /**
     *
     * @param techId
     * @return
     */
    AcadTechDetailDTO detailById (String techId);


    /**
     * 添加
     * @param jtAcadTechDTO
     */
    void addAcadTechDTO (JtAcadTechDTO jtAcadTechDTO);

    /**
     * 添加
     * @param acadTechAddDTO
     */
    void addAcadTechDTO (AcadTechAddDTO acadTechAddDTO);

    /**
     * 更新
     * @param
     */
   void updateAcadTechDTO (JtAcadTechDTO jtAcadTechDTO);

    /**
     *
     * @param acadTechUpdateDTO
     */
    void updateAcadTechDTO (AcadTechUpdateDTO acadTechUpdateDTO);

    /**
     *  删除
     * @param id
     * @return
     */
    int delectAcadTech (String id);



}

package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.ResearchProjAddDTO;
import com.lhsz.bandian.jt.DTO.request.ResearchProjDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ResearchProjUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.ResearchProjDTO;
import com.lhsz.bandian.jt.entity.ResearchProj;
import com.lhsz.bandian.jt.entity.ResearchProj;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 主持参与科研项目 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IResearchProjService extends IService<ResearchProj> {
    List<ResearchProjDTO> list(ResearchProj researchProj);

    void update(ResearchProjUpdateDTO researchProjUpdateDTO);

    void update(ResearchProjDTO researchProjDTO);

    void add(ResearchProjAddDTO researchProjAddDTO);

    void add(ResearchProjDTO researchProjDTO);

    ResearchProjDetailDTO detailById(String id);

    ResearchProjDTO selectById(String id);

    int del(String id);
}

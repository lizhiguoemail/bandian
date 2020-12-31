package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.EngnTechProjAddDTO;
import com.lhsz.bandian.jt.DTO.request.EngnTechProjDetailDTO;
import com.lhsz.bandian.jt.DTO.request.EngnTechProjUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.EngnTechProjDTO;
import com.lhsz.bandian.jt.entity.EngnTechProj;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 主持参与工程技术项目 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IEngnTechProjService extends IService<EngnTechProj> {
    boolean add(EngnTechProjAddDTO engnTechProj);

    boolean add(EngnTechProj engnTechProj);

    void update(EngnTechProj engnTechProj);

    void update(EngnTechProjUpdateDTO engnTechProj);

    List<EngnTechProjDTO> list(EngnTechProj engnTechProj);

    EngnTechProjDTO selectById(String id);

    EngnTechProjDetailDTO detailById(String id);

    int del(String id);
}

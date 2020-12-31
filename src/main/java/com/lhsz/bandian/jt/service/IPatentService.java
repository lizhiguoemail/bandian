package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.PatentAddDTO;
import com.lhsz.bandian.jt.DTO.request.PatentDetailDTO;
import com.lhsz.bandian.jt.DTO.request.PatentUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.PatentDTO;
import com.lhsz.bandian.jt.entity.Patent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 专利(著作权) 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IPatentService extends IService<Patent> {
    List<PatentDTO> list(Patent patent);

    void update(PatentUpdateDTO patentUpdateDTO);

    void update(PatentDTO patentDTO);

    void add(PatentAddDTO patentAddDTO);

    void add(PatentDTO patentDTO);

    PatentDTO selectById(String id);

    PatentDetailDTO detailById(String id);

    int del(String id);
}

package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.ThesisAddDTO;
import com.lhsz.bandian.jt.DTO.request.ThesisDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ThesisUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.ThesisDTO;
import com.lhsz.bandian.jt.entity.Thesis;
import com.lhsz.bandian.jt.entity.Thesis;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 论文 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IThesisService extends IService<Thesis> {
    List<ThesisDTO> list(Thesis Thesis);

    void update(ThesisUpdateDTO thesisUpdateDTO);

    void update(ThesisDTO thesisDTO);

    void add(ThesisAddDTO thesisAddDTO);

    void add(ThesisDTO thesisDTO);

    ThesisDetailDTO detailById(String id);

    ThesisDTO selectById(String id);

    int del(String id);
}

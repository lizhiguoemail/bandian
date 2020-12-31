package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.ResultUsedAddDTO;
import com.lhsz.bandian.jt.DTO.request.ResultUsedDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ResultUsedUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.ResultUsedDTO;
import com.lhsz.bandian.jt.entity.ResultUsed;
import com.lhsz.bandian.jt.entity.ResultUsed;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 成果被批示、采纳、运用和推广 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IResultUsedService extends IService<ResultUsed> {
    List<ResultUsedDTO> list(ResultUsed resultUsed);

    void update(ResultUsedUpdateDTO resultUsedUpdateDTO);

    void update(ResultUsedDTO resultUsedDTO);

    void add(ResultUsedAddDTO resultUsedAddDTO);

    void add(ResultUsedDTO resultUsedDTO);

    ResultUsedDetailDTO detailById(String id);

    ResultUsedDTO selectById(String id);

    int del(String id);
}

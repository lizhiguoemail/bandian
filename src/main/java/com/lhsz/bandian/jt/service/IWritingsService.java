package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.WritingsAddDTO;
import com.lhsz.bandian.jt.DTO.request.WritingsDetailDTO;
import com.lhsz.bandian.jt.DTO.request.WritingsUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.WritingsDTO;
import com.lhsz.bandian.jt.entity.Writings;
import com.lhsz.bandian.jt.entity.Writings;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 著(译)作(教材) 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IWritingsService extends IService<Writings> {
    List<WritingsDTO> list(Writings writings);
    void update(WritingsDTO writingsDTO);
    void update(WritingsUpdateDTO writingsDTO);
    void add(WritingsDTO writingsDTO);
    void add(WritingsAddDTO writingsDTO);
    WritingsDTO selectById(String id);
    WritingsDetailDTO detailById(String id);
    int del(String id);
}

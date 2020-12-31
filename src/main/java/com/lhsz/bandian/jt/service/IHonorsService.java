package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.HonorsAddDTO;
import com.lhsz.bandian.jt.DTO.request.HonorsDetailDTO;
import com.lhsz.bandian.jt.DTO.request.HonorsUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.HonorsDTO;
import com.lhsz.bandian.jt.entity.Honors;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 获得荣誉 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IHonorsService extends IService<Honors> {
    boolean add(Honors honors);

    boolean add(HonorsAddDTO honors);

    void update(HonorsDTO honorsDTO);

    void update(HonorsUpdateDTO honorsDTO);

    List<HonorsDTO> list(Honors honors);

    HonorsDTO selectById(String id);

    HonorsDetailDTO detailById(String id);

    int del(String id);

}

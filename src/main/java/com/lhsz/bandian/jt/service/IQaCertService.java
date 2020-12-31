package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.QaCertAddDTO;
import com.lhsz.bandian.jt.DTO.request.QaCertDetailDTO;
import com.lhsz.bandian.jt.DTO.request.QaCertUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.QaCertDTO;
import com.lhsz.bandian.jt.entity.QaCert;
import com.lhsz.bandian.jt.entity.QaCert;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资质证书 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IQaCertService extends IService<QaCert> {
    List<QaCertDTO> list(QaCert qaCert);

    void update(QaCertUpdateDTO qaCertAddDTO);

    void update(QaCertDTO qaCertDTO);

    void add(QaCertAddDTO qaCertAddDTO);

    void add(QaCertDTO qaCertDTO);

    QaCertDetailDTO detailById(String id);

    QaCertDTO selectById(String id);

    int del(String id);
}

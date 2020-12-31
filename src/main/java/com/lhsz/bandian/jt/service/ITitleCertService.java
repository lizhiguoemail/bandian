package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.response.TitleCertDTO;
import com.lhsz.bandian.jt.entity.TitleCert;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 职称证书 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface ITitleCertService extends IService<TitleCert> {
    List<TitleCertDTO> list(TitleCert TitleCert);
    void update(TitleCertDTO TitleCertDTO);
    void add(TitleCertDTO TitleCertDTO);
    TitleCertDTO selectById(String id);
    int del(String id);
}

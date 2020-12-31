package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.MakeStandardAddDTO;
import com.lhsz.bandian.jt.DTO.request.MakeStandardDetailDTO;
import com.lhsz.bandian.jt.DTO.request.MakeStandardUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.MakeStandardDTO;
import com.lhsz.bandian.jt.entity.MakeStandard;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 主持(参与)制定标准 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IMakeStandardService extends IService<MakeStandard> {
    boolean add(MakeStandard makeStandard);

    boolean add(MakeStandardAddDTO makeStandard);

    void update(MakeStandard makeStandard);

    void update(MakeStandardUpdateDTO makeStandard);

    List<MakeStandardDTO> list(MakeStandard makeStandard);

    MakeStandardDTO selectById(String id);

    MakeStandardDetailDTO detailById(String id);

    int del(String id);

}

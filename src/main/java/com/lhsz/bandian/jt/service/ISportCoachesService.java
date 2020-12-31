package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.SportCoachesAddDTO;
import com.lhsz.bandian.jt.DTO.request.SportCoachesDetailDTO;
import com.lhsz.bandian.jt.DTO.request.SportCoachesUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.SportCoachesDTO;
import com.lhsz.bandian.jt.entity.SportCoaches;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 培养输送运动员 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface ISportCoachesService extends IService<SportCoaches> {
    List<SportCoachesDTO> list(SportCoaches sportCoaches);

    void update(SportCoachesUpdateDTO sportCoachesUpdateDTO);

    void update(SportCoachesDTO sportCoachesDTO);

    void add(SportCoachesAddDTO sportCoachesAddDTO);

    void add(SportCoachesDTO sportCoachesDTO);

    SportCoachesDetailDTO detailById(String id);

    SportCoachesDTO selectById(String id);

    int del(String id);
}

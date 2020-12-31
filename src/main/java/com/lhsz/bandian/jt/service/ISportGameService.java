package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.SportGameAddDTO;
import com.lhsz.bandian.jt.DTO.request.SportGameDetailDTO;
import com.lhsz.bandian.jt.DTO.request.SportGameUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.SportGameDTO;
import com.lhsz.bandian.jt.entity.SportGame;
import com.lhsz.bandian.jt.entity.SportGame;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 竞技体育比赛成果 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface ISportGameService extends IService<SportGame> {
    List<SportGameDTO> list(SportGame sportGame);

    void update(SportGameUpdateDTO sportGameUpdateDTO);

    void update(SportGameDTO sportGameDTO);

    void add(SportGameAddDTO sportGameAddDTO);

    void add(SportGameDTO sportGameDTO);

    SportGameDetailDTO detailById(String id);

    SportGameDTO selectById(String id);

    int del(String id);
}

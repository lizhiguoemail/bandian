package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.SportGameDTO;
import com.lhsz.bandian.jt.entity.SportGame;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 竞技体育比赛成果 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface SportGameMapper extends MyBaseMapper<SportGame> {
    List<SportGameDTO> selectMapperList(SportGame sportGame);
    SportGameDTO selectDTO (@Param("id")String id);
}

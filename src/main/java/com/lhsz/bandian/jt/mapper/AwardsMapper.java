package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.JtAwardsDTO;
import com.lhsz.bandian.jt.entity.Awards;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 获奖情况 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface AwardsMapper extends MyBaseMapper<Awards> {
    List<JtAwardsDTO> selectMapperList(Awards awards);
    JtAwardsDTO selectDTO (@Param("id")String id);
}

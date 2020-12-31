package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.SportCoachesDTO;
import com.lhsz.bandian.jt.entity.SportCoaches;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 培养输送运动员 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface SportCoachesMapper extends MyBaseMapper<SportCoaches> {
    List<SportCoachesDTO> selectMapperList(SportCoaches sportCoaches);
    SportCoachesDTO selectDTO (@Param("id")String id);
}

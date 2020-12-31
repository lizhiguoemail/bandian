package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.IncentiveDTO;
import com.lhsz.bandian.jt.entity.Incentive;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 奖惩情况 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Repository
public interface IncentiveMapper extends MyBaseMapper<Incentive> {
    List<IncentiveDTO> selectMapperList(Incentive incentive);

    IncentiveDTO selectDTO(@Param("id")String id);
}

package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.JuryDeclarePlanDTO;
import com.lhsz.bandian.jt.entity.JuryDeclarePlan;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评委会申报计划 Mapper 接口
 * </p>
 *
 * @author zhusenlin
 * @since 2020-08-03
 */
public interface JuryDeclarePlanMapper extends MyBaseMapper<JuryDeclarePlan> {
    List<JuryDeclarePlanDTO> selectMapperList(JuryDeclarePlan juryDeclarePlan);

    JuryDeclarePlanDTO selectDTO(@Param("id")String id);
}

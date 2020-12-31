package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.MakeStandardDTO;
import com.lhsz.bandian.jt.entity.MakeStandard;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 主持(参与)制定标准 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Repository
public interface MakeStandardMapper extends MyBaseMapper<MakeStandard> {
    List<MakeStandardDTO> selectMapperList(MakeStandard makeStandard);

    MakeStandardDTO selectDTO(@Param("id")String id);
}

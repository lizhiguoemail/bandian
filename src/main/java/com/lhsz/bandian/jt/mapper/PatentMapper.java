package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.PatentDTO;
import com.lhsz.bandian.jt.entity.Patent;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 专利(著作权) Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface PatentMapper extends MyBaseMapper<Patent> {
    List<PatentDTO> selectMapperList(Patent patent);
    PatentDTO selectDTO (@Param("id")String id);
}

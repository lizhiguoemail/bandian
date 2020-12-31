package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.ThesisDTO;
import com.lhsz.bandian.jt.entity.Thesis;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 论文 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface ThesisMapper extends MyBaseMapper<Thesis> {
    List<ThesisDTO> selectMapperList(Thesis thesis);
    ThesisDTO selectDTO (@Param("id")String id);
}

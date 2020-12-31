package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.JtAssessmentDTO;
import com.lhsz.bandian.jt.entity.Assessment;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 任职考核 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface AssessmentMapper extends MyBaseMapper<Assessment> {
    List<JtAssessmentDTO> selectMapperList(Assessment assessment);
    JtAssessmentDTO selectDTO (@Param("id")String id);
}

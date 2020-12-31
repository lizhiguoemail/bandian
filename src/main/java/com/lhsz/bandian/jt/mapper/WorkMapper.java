package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.WorkDTO;
import com.lhsz.bandian.jt.entity.Work;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工作经历 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface WorkMapper extends MyBaseMapper<Work> {
    List<WorkDTO> selectMapperList(Work work);
    WorkDTO selectDTO (@Param("id")String id);
}

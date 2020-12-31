package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.ResearchProjDTO;
import com.lhsz.bandian.jt.entity.ResearchProj;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 主持参与科研项目 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface ResearchProjMapper extends MyBaseMapper<ResearchProj> {
    List<ResearchProjDTO> selectMapperList(ResearchProj researchProj);
    ResearchProjDTO selectDTO (@Param("id")String id);
}

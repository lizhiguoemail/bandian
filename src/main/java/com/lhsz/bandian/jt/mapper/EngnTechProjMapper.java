package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.EngnTechProjDTO;
import com.lhsz.bandian.jt.entity.EngnTechProj;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 主持参与工程技术项目 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface EngnTechProjMapper extends MyBaseMapper<EngnTechProj> {
    List<EngnTechProjDTO> selectMapperList(EngnTechProj engnTechProj);

    EngnTechProjDTO selectDTO(@Param("id")String id);
}

package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.JtEduDTO;
import com.lhsz.bandian.jt.entity.Edu;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 教育经历 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface EduMapper extends MyBaseMapper<Edu> {
    List<JtEduDTO> selectMapperList(Edu edu);
    JtEduDTO selectDTO (@Param("id")String id);
}

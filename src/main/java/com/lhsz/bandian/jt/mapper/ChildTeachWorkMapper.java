package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.JtChildTeachWorkDTO;
import com.lhsz.bandian.jt.entity.ChildTeachWork;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 中小学幼儿老师日常教学 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface ChildTeachWorkMapper extends MyBaseMapper<ChildTeachWork> {
    List<JtChildTeachWorkDTO> selectMapperList(ChildTeachWork childTeachWork);
    JtChildTeachWorkDTO selectDTO (@Param("id")String id);
}

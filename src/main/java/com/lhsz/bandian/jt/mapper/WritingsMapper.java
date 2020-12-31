package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.WritingsDTO;
import com.lhsz.bandian.jt.entity.Writings;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 著(译)作(教材) Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface WritingsMapper extends MyBaseMapper<Writings> {
    List<WritingsDTO> selectMapperList(Writings writings);
    WritingsDTO selectDTO (@Param("id")String id);
}

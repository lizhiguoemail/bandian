package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.HonorsDTO;
import com.lhsz.bandian.jt.entity.Honors;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 获得荣誉 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Repository
public interface HonorsMapper extends MyBaseMapper<Honors> {
    List<HonorsDTO> selectMapperList(Honors Honors);

    HonorsDTO selectDTO(@Param("id")String id);
}

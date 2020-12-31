package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.TrainingEduDTO;
import com.lhsz.bandian.jt.entity.TrainingEdu;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 继续教育 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface TrainingEduMapper extends MyBaseMapper<TrainingEdu> {
    List<TrainingEduDTO> selectMapperList(TrainingEdu trainingEdu);
    TrainingEduDTO selectDTO (@Param("id")String id);
}

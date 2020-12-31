package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.EngnTechProjDTO;
import com.lhsz.bandian.jt.DTO.response.JuryDeclareSubjectDTO;
import com.lhsz.bandian.jt.DTO.response.JuryDeptDTO;
import org.apache.ibatis.annotations.Param;
import com.lhsz.bandian.jt.entity.JuryDeclareSubject;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 评委会申报对象资格 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Repository
public interface JuryDeclareSubjectMapper extends MyBaseMapper<JuryDeclareSubject> {
    List<JuryDeclareSubjectDTO> selectMapperList (JuryDeclareSubject juryDeclareSubject);
    List<JuryDeptDTO> search(@Param("declareCategory") String declareCategory, @Param("declareSpecialty") String declareSpecialty, @Param("declareTitle") String declareTitle, @Param("province") String province, @Param("city") String city, @Param("county") String county);
}

package com.lhsz.bandian.jt.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.jt.DTO.response.JuryDeptDTO;
import com.lhsz.bandian.jt.DTO.response.JuryUserDTO;
import com.lhsz.bandian.jt.entity.JuryDept;
import com.lhsz.bandian.mapper.MyBaseMapper;
import com.lhsz.bandian.sys.DTO.query.QueryUserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 评委会主管部门 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Repository
public interface JuryDeptMapper extends MyBaseMapper<JuryDept> {
    List<JuryUserDTO> listDTO(@Param("ew") QueryWrapper<QueryUserDTO> queryWrapper);

    /**
     * 查询只有一个账户并且账户为禁用状态的评委会
     */
    List<JuryDeptDTO> listDisableDTO(JuryDept juryDept);

    List<JuryDeptDTO> selectListJury(JuryDeptDTO juryDeptDTO, @Param("userId") String userId);
}

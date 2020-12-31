package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.entity.JuryDeptUser;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 评委会主管部门用户 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Repository
public interface JuryDeptUserMapper extends MyBaseMapper<JuryDeptUser> {

    @Delete("delete from jt_jury_dept_user where user_id = #{userId}")
    void trueToDelete(String userId);
    @Delete("delete from jt_jury_dept_user where dept_id = #{deptId}")
    void trueToDeleteByDeptId(String deptId);
}

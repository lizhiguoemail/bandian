package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.response.UserProfileDTO;
import com.lhsz.bandian.jt.entity.UserProfile;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 基本信息 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface UserProfileMapper extends MyBaseMapper<UserProfile> {
    UserProfileDTO selectDTO(@Param("id")String id);
    List<UserProfileDTO> selectJtList(UserProfile userProfile);
}

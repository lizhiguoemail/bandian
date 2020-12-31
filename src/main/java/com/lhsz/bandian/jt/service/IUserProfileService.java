package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.UserProfileAddDTO;
import com.lhsz.bandian.jt.DTO.request.UserProfileDetailDTO;
import com.lhsz.bandian.jt.DTO.request.UserProfileUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.UserProfileDTO;
import com.lhsz.bandian.jt.entity.UserProfile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 基本信息 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IUserProfileService extends IService<UserProfile> {
    List<UserProfileDTO> list(UserProfile userProfile);

    void update(UserProfileUpdateDTO userProfileUpdateDTO);

    void update(UserProfileDTO userProfileDTO);

    void add(UserProfileAddDTO userProfileAddDTO);

    void add(UserProfileDTO userProfileDTO);

    UserProfileDetailDTO detailById(String id);

    UserProfileDTO selectById(String id);

    int del(String id);

    UserProfile findByCertNo(String certNo);

    boolean updateCertPhotoById(String id, String certPhoto);
}

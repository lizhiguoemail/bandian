package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lhsz.bandian.jt.DTO.request.UserProfileAddDTO;
import com.lhsz.bandian.jt.DTO.request.UserProfileDetailDTO;
import com.lhsz.bandian.jt.DTO.request.UserProfileUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.UserProfileDTO;
import com.lhsz.bandian.jt.entity.UserProfile;
import com.lhsz.bandian.jt.mapper.UserProfileMapper;
import com.lhsz.bandian.jt.service.IUserProfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IAttachService;
import com.lhsz.bandian.sys.service.IDictDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 基本信息 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile> implements IUserProfileService {
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDictDataService iDictDataService;

    @Autowired
    private IAttachService attachService;
    @Override
    public List<UserProfileDTO> list(UserProfile userProfile) {
        List<UserProfileDTO> userProfileDTOS = userProfileMapper.selectJtList(userProfile);
        return userProfileDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(UserProfileUpdateDTO userProfileUpdateDTO) {
        UserProfile userProfile1 = userProfileMapper.selectById(userProfileUpdateDTO.getData().getUserId());
        if (userProfile1!=null){
            UserProfile userProfile = new UserProfile();
            BeanUtils.copyProperties(userProfileUpdateDTO.getData(),userProfile);
            userProfile.setUserId(userProfileUpdateDTO.getData().getId());
            updateById(userProfile);

            if (userProfileUpdateDTO.getFiles().size() > 0) {
                List<Attach> attachs = new ArrayList<>();
                userProfileUpdateDTO.getFiles().forEach(obj -> {
                    obj.setObjectId(userProfileUpdateDTO.getData().getUserId());
                    obj.setObjectType("业绩库");
                    attachs.add(FileUploadDTO.ToAttach(obj));
                });
                attachService.saveOrUpdateAttachBatch(attachs);
            }
        }else {
            String userId = UUID.randomUUID().toString();
            UserProfile userProfile = new UserProfile();
            userProfileUpdateDTO.getData().setIsDeleted(0);
            User loginUserToUser = tokenService.getLoginUserToUser();
            userProfileUpdateDTO.getData().setUserId(loginUserToUser.getUserId());
            BeanUtils.copyProperties(userProfileUpdateDTO.getData(),userProfile);
            userProfileUpdateDTO.getData().setId(userProfile.getUserId());
            save(userProfile);
            if (userProfileUpdateDTO.getFiles().size() > 0) {
                List<Attach> attachs = new ArrayList<>();
                userProfileUpdateDTO.getFiles().forEach(obj -> {
                    obj.setObjectId(userId);
                    obj.setObjectType("业绩库");
                    attachs.add(FileUploadDTO.ToAttach(obj));
                });
                attachService.saveAttachBatch(attachs);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(UserProfileDTO userProfileDTO) {
        UserProfile userProfile = new UserProfile();
        BeanUtils.copyProperties(userProfileDTO,userProfile);
        userProfile.setUserId(userProfileDTO.getId());
        updateById(userProfile);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(UserProfileAddDTO userProfileAddDTO) {
        UserProfile userProfile1 = userProfileMapper.selectById(userProfileAddDTO.getData().getUserId());
        if (userProfile1!=null){
            UserProfile userProfile = new UserProfile();
            BeanUtils.copyProperties(userProfileAddDTO.getData(),userProfile);
            userProfile.setUserId(userProfileAddDTO.getData().getId());
            updateById(userProfile);

            if (userProfileAddDTO.getFiles().size() > 0) {
                List<Attach> attachs = new ArrayList<>();
                userProfileAddDTO.getFiles().forEach(obj -> {
                    obj.setObjectId(userProfileAddDTO.getData().getUserId());
                    obj.setObjectType("业绩库");
                    attachs.add(FileUploadDTO.ToAttach(obj));
                });
                attachService.saveOrUpdateAttachBatch(attachs);
            }
        }else {
            String userId = UUID.randomUUID().toString();
            UserProfile userProfile = new UserProfile();
            userProfileAddDTO.getData().setIsDeleted(0);
            User loginUserToUser = tokenService.getLoginUserToUser();
            userProfileAddDTO.getData().setUserId(loginUserToUser.getUserId());
            BeanUtils.copyProperties(userProfileAddDTO.getData(),userProfile);
            userProfileAddDTO.getData().setId(userProfile.getUserId());
            save(userProfile);
            if (userProfileAddDTO.getFiles().size() > 0) {
                List<Attach> attachs = new ArrayList<>();
                userProfileAddDTO.getFiles().forEach(obj -> {
                    obj.setObjectId(userId);
                    obj.setObjectType("业绩库");
                    attachs.add(FileUploadDTO.ToAttach(obj));
                });
                attachService.saveAttachBatch(attachs);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(UserProfileDTO userProfileDTO) {
        UserProfile userProfile = new UserProfile();
        userProfileDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        userProfileDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(userProfileDTO,userProfile);
        userProfileDTO.setId(userProfile.getUserId());
        save(userProfile);
    }

    @Override
    public UserProfileDetailDTO detailById(String id) {
        UserProfileDetailDTO userProfileDetailDTO = new UserProfileDetailDTO();
//        UserProfile userProfile = UserProfileMapper.selectById(id);
        UserProfileDTO userProfileDTO = userProfileMapper.selectDTO(id);
        userProfileDetailDTO.setData(userProfileDTO);

        List<FileUploadDTO> fileUploadDTOS = attachService.listByObjectId(userProfileDTO.getUserId());
        userProfileDetailDTO.setFiles(fileUploadDTOS);
        return userProfileDetailDTO;
    }

    @Override
    public UserProfileDTO selectById(String id) {
        UserProfile userProfile = userProfileMapper.selectById(id);
        if(userProfile != null){
            UserProfileDTO userProfileDTO = new UserProfileDTO(userProfile);
            return userProfileDTO;
        }
        return null;
    }

    @Override
    public int del(String id) {
        return userProfileMapper.deleteById(id);
    }

    @Override
    public UserProfile findByCertNo(String certNo) {
        UserProfile userProfile = getOne(new QueryWrapper<UserProfile>().eq("cert_no", certNo));
        return userProfile;
    }

    @Override
    public boolean updateCertPhotoById(String id, String certPhoto) {
        UserProfile userProfile = new UserProfile();
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("user_id", id);
        updateWrapper.set("cert_photo", certPhoto);
        return update(updateWrapper);
    }
}

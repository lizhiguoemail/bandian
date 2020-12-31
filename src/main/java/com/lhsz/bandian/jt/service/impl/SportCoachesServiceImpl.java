package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.jt.DTO.request.SportCoachesAddDTO;
import com.lhsz.bandian.jt.DTO.request.SportCoachesDetailDTO;
import com.lhsz.bandian.jt.DTO.request.SportCoachesUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.SportCoachesDTO;
import com.lhsz.bandian.jt.entity.SportCoaches;
import com.lhsz.bandian.jt.mapper.SportCoachesMapper;
import com.lhsz.bandian.jt.service.ISportCoachesService;
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
 * 培养输送运动员 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class SportCoachesServiceImpl extends ServiceImpl<SportCoachesMapper, SportCoaches> implements ISportCoachesService {
    @Autowired
    private com.lhsz.bandian.jt.mapper.SportCoachesMapper SportCoachesMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDictDataService iDictDataService;

    @Autowired
    private IAttachService attachService;
    @Override
    public List<SportCoachesDTO> list(SportCoaches sportCoaches) {
        List<SportCoachesDTO> sportCoachesDTOS = SportCoachesMapper.selectMapperList(sportCoaches);
        return sportCoachesDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(SportCoachesUpdateDTO sportCoachesUpdateDTO) {
        SportCoaches sportCoaches = new SportCoaches();
        BeanUtils.copyProperties(sportCoachesUpdateDTO.getData(),sportCoaches);
        sportCoaches.setTrainingId(sportCoachesUpdateDTO.getData().getId());
        updateById(sportCoaches);

        if (sportCoachesUpdateDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            sportCoachesUpdateDTO.getFiles().forEach(obj -> {
                obj.setObjectId(sportCoachesUpdateDTO.getData().getTrainingId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(SportCoachesDTO sportCoachesDTO) {
        SportCoaches sportCoaches = new SportCoaches();
        BeanUtils.copyProperties(sportCoachesDTO,sportCoaches);
        sportCoaches.setTrainingId(sportCoachesDTO.getId());
        updateById(sportCoaches);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(SportCoachesAddDTO sportCoachesAddDTO) {
        String trainingId = UUID.randomUUID().toString();
        SportCoaches sportCoaches = new SportCoaches();
        sportCoachesAddDTO.getData().setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        sportCoachesAddDTO.getData().setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(sportCoachesAddDTO.getData(),sportCoaches);
        sportCoachesAddDTO.getData().setId(sportCoaches.getTrainingId());
        save(sportCoaches);
        if (sportCoachesAddDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            sportCoachesAddDTO.getFiles().forEach(obj -> {
                obj.setObjectId(trainingId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(SportCoachesDTO sportCoachesDTO) {
        SportCoaches sportCoaches = new SportCoaches();
        sportCoachesDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        sportCoachesDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(sportCoachesDTO,sportCoaches);
        sportCoachesDTO.setId(sportCoaches.getTrainingId());

        save(sportCoaches);
    }

    @Override
    public SportCoachesDetailDTO detailById(String id) {
        SportCoachesDetailDTO sportCoachesDetailDTO = new SportCoachesDetailDTO();
        SportCoachesDTO sportCoaches = SportCoachesMapper.selectDTO(id);
        sportCoachesDetailDTO.setData(sportCoaches);
        List<FileUploadDTO> fileUploadDTOS = attachService.listByObjectId(sportCoaches.getTrainingId());
        sportCoachesDetailDTO.setFiles(fileUploadDTOS);
        return sportCoachesDetailDTO;
    }

    @Override
    public SportCoachesDTO selectById(String id) {
        SportCoaches sportCoaches = SportCoachesMapper.selectById(id);
        SportCoachesDTO sportCoachesDTO = new SportCoachesDTO(sportCoaches);
        return sportCoachesDTO;
    }

    @Override
    public int del(String id) {
        return SportCoachesMapper.deleteById(id);
    }
}

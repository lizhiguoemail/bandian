package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.jt.DTO.request.TrainingEduAddDTO;
import com.lhsz.bandian.jt.DTO.request.TrainingEduDetailDTO;
import com.lhsz.bandian.jt.DTO.request.TrainingEduUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.TrainingEduDTO;
import com.lhsz.bandian.jt.entity.TrainingEdu;
import com.lhsz.bandian.jt.mapper.TrainingEduMapper;
import com.lhsz.bandian.jt.service.ITrainingEduService;
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
 * 继续教育 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class TrainingEduServiceImpl extends ServiceImpl<TrainingEduMapper, TrainingEdu> implements ITrainingEduService {
    @Autowired
    private TrainingEduMapper trainingEduMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDictDataService iDictDataService;
    @Autowired
    private IAttachService attachService;
    @Override
    public List<TrainingEduDTO> list(TrainingEdu trainingEdu) {
        List<TrainingEduDTO> trainingEduDTOS = trainingEduMapper.selectMapperList(trainingEdu);
        return trainingEduDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(TrainingEduUpdateDTO trainingEduUpdateDTO) {
        TrainingEdu trainingEdu = new TrainingEdu();
        BeanUtils.copyProperties(trainingEduUpdateDTO.getData(),trainingEdu);
        trainingEdu.setEduId(trainingEduUpdateDTO.getData().getId());
        updateById(trainingEdu);


        if (trainingEduUpdateDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            trainingEduUpdateDTO.getFiles().forEach(obj -> {
                obj.setObjectId(trainingEduUpdateDTO.getData().getEduId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(TrainingEduDTO trainingEduDTO) {
        TrainingEdu trainingEdu = new TrainingEdu();
        BeanUtils.copyProperties(trainingEduDTO,trainingEdu);
        trainingEdu.setEduId(trainingEduDTO.getId());
        updateById(trainingEdu);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(TrainingEduAddDTO trainingEduAddDTO) {
        String eduId = UUID.randomUUID().toString();
        TrainingEdu trainingEdu = new TrainingEdu();
        trainingEduAddDTO.getData().setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        trainingEduAddDTO.getData().setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(trainingEduAddDTO.getData(),trainingEdu);
        trainingEduAddDTO.getData().setId(trainingEdu.getEduId());
        save(trainingEdu);
        if (trainingEduAddDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            trainingEduAddDTO.getFiles().forEach(obj -> {
                obj.setObjectId(eduId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(TrainingEduDTO trainingEduDTO) {
        TrainingEdu trainingEdu = new TrainingEdu();
        trainingEduDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        trainingEduDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(trainingEduDTO,trainingEdu);
        trainingEduDTO.setId(trainingEdu.getEduId());

        save(trainingEdu);
    }

    @Override
    public TrainingEduDetailDTO detailById(String id) {
        TrainingEduDetailDTO trainingEduDetailDTO = new TrainingEduDetailDTO();
        TrainingEduDTO trainingEdu = trainingEduMapper.selectDTO(id);
        trainingEduDetailDTO.setData(trainingEdu);
        List<FileUploadDTO> fileUploadDTOS = attachService.listByObjectId(trainingEdu.getEduId());
        trainingEduDetailDTO.setFiles(fileUploadDTOS);
        return trainingEduDetailDTO;
    }

    @Override
    public TrainingEduDTO selectById(String id) {
        TrainingEdu trainingEdu = trainingEduMapper.selectById(id);
        TrainingEduDTO trainingEduDTO = new TrainingEduDTO(trainingEdu);
        return trainingEduDTO;
    }

    @Override
    public int del(String id) {
        return trainingEduMapper.deleteById(id);
    }
}

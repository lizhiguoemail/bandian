package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.TrainingEduAddDTO;
import com.lhsz.bandian.jt.DTO.request.TrainingEduDetailDTO;
import com.lhsz.bandian.jt.DTO.request.TrainingEduUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.TrainingEduDTO;
import com.lhsz.bandian.jt.entity.TrainingEdu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 继续教育 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface ITrainingEduService extends IService<TrainingEdu> {
    List<TrainingEduDTO> list(TrainingEdu TrainingEdu);

    void update(TrainingEduUpdateDTO trainingEduUpdateDTO);

    void update(TrainingEduDTO TrainingEduDTO);

    void add(TrainingEduAddDTO trainingEduAddDTO);

    void add(TrainingEduDTO TrainingEduDTO);

    TrainingEduDetailDTO detailById(String id);

    TrainingEduDTO selectById(String id);

    int del(String id);
}

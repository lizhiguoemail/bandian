package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.AcadTechDetailDTO;
import com.lhsz.bandian.jt.DTO.request.AssessmentAddDTO;
import com.lhsz.bandian.jt.DTO.request.AssessmentDetailDTO;
import com.lhsz.bandian.jt.DTO.request.AssessmentUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtAssessmentDTO;
import com.lhsz.bandian.jt.entity.Assessment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 任职考核 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IAssessmentService extends IService<Assessment> {

    /**
     * 根据jtAssessmentDTO对象查询
     * @param assessment
     * @return
     */
    List<JtAssessmentDTO> listAssessment (Assessment assessment);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    JtAssessmentDTO getAssessment (String id);

    /**
     * 查询
     * @param id
     * @return
     */
    AssessmentDetailDTO detailById (String id);

    /**
     * 添加
     * @param jtAssessmentDTO
     * @return
     */
    void addAssessment (JtAssessmentDTO jtAssessmentDTO);

    /**
     * 添加
     * @param jtAssessmentDTO
     */
    void addAssessment (AssessmentAddDTO jtAssessmentDTO);

    /**
     * 更新
     * @param jtAssessmentDTO
     * @return
     */
    void updateAssessment (JtAssessmentDTO jtAssessmentDTO);

    /**
     * 更新
     * @param jtAssessmentDTO
     */
    void updateAssessment (AssessmentUpdateDTO jtAssessmentDTO);

    /**
     * 删除
     * @param id
     * @return
     */
    int delAssessment (String id);

}

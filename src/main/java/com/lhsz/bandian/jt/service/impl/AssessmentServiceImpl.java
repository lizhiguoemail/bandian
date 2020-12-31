package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.AssessmentAddDTO;
import com.lhsz.bandian.jt.DTO.request.AssessmentDetailDTO;
import com.lhsz.bandian.jt.DTO.request.AssessmentUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtAssessmentDTO;
import com.lhsz.bandian.jt.entity.Assessment;
import com.lhsz.bandian.jt.mapper.AssessmentMapper;
import com.lhsz.bandian.jt.service.IAssessmentService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
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
 * 任职考核 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class AssessmentServiceImpl extends ServiceImpl<AssessmentMapper, Assessment> implements IAssessmentService {

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Autowired
    private IDictDataService iDictDataService;

    @Autowired
    private IAttachService attachService;

    @Override
    public List<JtAssessmentDTO> listAssessment(Assessment assessment) {
        List<JtAssessmentDTO> jtAssessmentDTOS = assessmentMapper.selectMapperList(assessment);
        return jtAssessmentDTOS;
    }

    @Override
    public JtAssessmentDTO getAssessment(String id) {
        Assessment assessment = assessmentMapper.selectById(id);
        JtAssessmentDTO jtAssessmentDTO = new JtAssessmentDTO();
        BeanUtils.copyProperties(assessment,jtAssessmentDTO);
        jtAssessmentDTO.setId(id);
        return jtAssessmentDTO;
    }

    @Override
    public AssessmentDetailDTO detailById(String id) {
        AssessmentDetailDTO detailDTO = new AssessmentDetailDTO();
        JtAssessmentDTO assessment = assessmentMapper.selectDTO(id);
        detailDTO.setData(assessment);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(id);
        detailDTO.setFiles(fileUploadDTOS);
        return detailDTO;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addAssessment(JtAssessmentDTO jtAssessmentDTO) {
        Assessment assessment = new Assessment();
        BeanUtils.copyProperties(jtAssessmentDTO,assessment);
        jtAssessmentDTO.setId(assessment.getAssessmentId());
        save(assessment);
    }

    @Override
    public void addAssessment(AssessmentAddDTO jtAssessmentDTO) {
        String Id = UUID.randomUUID().toString();
        jtAssessmentDTO.getData().setAssessmentId(Id);
        jtAssessmentDTO.getData().setIsDeleted(0);
        save(jtAssessmentDTO.getData());
        if (jtAssessmentDTO.getFiles().size() > 0 ){
            List<Attach> attachs = new ArrayList<>();
            jtAssessmentDTO.getFiles().forEach(obj ->{
                obj.setObjectId(Id);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateAssessment(JtAssessmentDTO jtAssessmentDTO) {
        Assessment assessment = new Assessment();
        BeanUtils.copyProperties(jtAssessmentDTO,assessment);
        assessment.setAssessmentId(jtAssessmentDTO.getId());
        updateById(assessment);
    }

    @Override
    public void updateAssessment(AssessmentUpdateDTO jtAssessmentDTO) {
        updateById(jtAssessmentDTO.getData());
        if (jtAssessmentDTO.getFiles().size() > 0 ){
            List<Attach> attachs = new ArrayList<>();
            jtAssessmentDTO.getFiles().forEach(obj ->{
                obj.setObjectId(jtAssessmentDTO.getData().getAssessmentId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }

    }

    @Override
    public int delAssessment(String id) {
        int deleteById = assessmentMapper.deleteById(id);
        return deleteById;
    }
}

package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.EduAddDTO;
import com.lhsz.bandian.jt.DTO.request.EduDetailDTO;
import com.lhsz.bandian.jt.DTO.request.EduUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtEduDTO;
import com.lhsz.bandian.jt.entity.Edu;
import com.lhsz.bandian.jt.mapper.EduMapper;
import com.lhsz.bandian.jt.service.IEduService;
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
 * 教育经历 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class EduServiceImpl extends ServiceImpl<EduMapper, Edu> implements IEduService {

    @Autowired
    private EduMapper eduMapper;

    @Autowired
    private IDictDataService iDictDataService;

    @Autowired
    private IAttachService attachService;

    @Override
    public List<JtEduDTO> listEdu(Edu edu) {
        List<JtEduDTO> jtEduDTOS = eduMapper.selectMapperList(edu);
        return jtEduDTOS;
    }

    @Override
    public JtEduDTO getEdu(String id) {
        Edu edu = eduMapper.selectById(id);
        JtEduDTO jtEduDTO = new JtEduDTO();
        BeanUtils.copyProperties(edu,jtEduDTO);
        jtEduDTO.setId(edu.getEduId());
        return jtEduDTO;
    }

    @Override
    public EduDetailDTO detailById(String id) {
        EduDetailDTO detailDTO = new EduDetailDTO();
        JtEduDTO edu = eduMapper.selectDTO(id);
        detailDTO.setData(edu);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(edu.getEduId());
        detailDTO.setFiles(fileUploadDTOS);
        return detailDTO;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addEdu(JtEduDTO jtEduDTO) {
        Edu edu = new Edu();
        BeanUtils.copyProperties(jtEduDTO,edu);
        jtEduDTO.setId(edu.getEduId());
        save(edu);

    }

    @Override
    public void addEdu(EduAddDTO jtEduDTO) {
        String Id = UUID.randomUUID().toString();
        jtEduDTO.getData().setEduId(Id);
        save(jtEduDTO.getData());
        if (jtEduDTO.getFiles().size() > 0 ){
            List<Attach> attachs = new ArrayList<>();
            jtEduDTO.getFiles().forEach(obj ->{
                obj.setObjectId(Id);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateEdu(JtEduDTO jtEduDTO) {
        Edu edu = new Edu();
        BeanUtils.copyProperties(jtEduDTO,edu);
        edu.setEduId(jtEduDTO.getId());
        updateById(edu);
    }

    @Override
    public void updateEdu(EduUpdateDTO jtEduDTO) {
        updateById(jtEduDTO.getData());
        if (jtEduDTO.getFiles().size() > 0 ){
            List<Attach> attachs = new ArrayList<>();
            jtEduDTO.getFiles().forEach(obj ->{
                obj.setObjectId(jtEduDTO.getData().getEduId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }


    @Override
    public int delEdu(String id) {
        return eduMapper.deleteById(id);
    }
}

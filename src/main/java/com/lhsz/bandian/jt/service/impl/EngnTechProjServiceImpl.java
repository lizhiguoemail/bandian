package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.EngnTechProjAddDTO;
import com.lhsz.bandian.jt.DTO.request.EngnTechProjDetailDTO;
import com.lhsz.bandian.jt.DTO.request.EngnTechProjUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.EngnTechProjDTO;
import com.lhsz.bandian.jt.entity.EngnTechProj;
import com.lhsz.bandian.jt.mapper.EngnTechProjMapper;
import com.lhsz.bandian.jt.service.IEngnTechProjService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.service.IAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 主持参与工程技术项目 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class EngnTechProjServiceImpl extends ServiceImpl<EngnTechProjMapper, EngnTechProj> implements IEngnTechProjService {

    private final EngnTechProjMapper engnTechProjMapper;

    private final IAttachService attachService;

    @Autowired
    public EngnTechProjServiceImpl(EngnTechProjMapper engnTechProjMapper, IAttachService attachService) {
        this.engnTechProjMapper = engnTechProjMapper;
        this.attachService = attachService;
    }

    @Override
    public boolean add(EngnTechProjAddDTO engnTechProj) {
        String projectId = UUID.randomUUID().toString();
        engnTechProj.getData().setProjectId(projectId);
        engnTechProj.getData().setBeginDate(engnTechProj.getData().getBeginDate().substring(0, 10));
        engnTechProj.getData().setEndDate(engnTechProj.getData().getEndDate().substring(0, 10));
        engnTechProj.getData().setIsDeleted(0);
        boolean saveState = save(engnTechProj.getData());

        if (engnTechProj.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            engnTechProj.getFiles().forEach(obj -> {
                obj.setObjectId(projectId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
        return saveState;
    }

    @Override
    public boolean add(EngnTechProj engnTechProj) {
        engnTechProj.setBeginDate(engnTechProj.getBeginDate().substring(0, 10));
        engnTechProj.setEndDate(engnTechProj.getEndDate().substring(0, 10));
        engnTechProj.setIsDeleted(0);
        boolean saveState = save(engnTechProj);
        return saveState;
    }

    @Override
    public void update(EngnTechProj engnTechProj) {
        engnTechProj.setBeginDate(engnTechProj.getBeginDate().substring(0, 10));
        engnTechProj.setEndDate(engnTechProj.getEndDate().substring(0, 10));
        updateById(engnTechProj);
    }

    @Override
    public void update(EngnTechProjUpdateDTO engnTechProj) {
        engnTechProj.getData().setBeginDate(engnTechProj.getData().getBeginDate().substring(0, 10));
        engnTechProj.getData().setEndDate(engnTechProj.getData().getEndDate().substring(0, 10));
        boolean saveState = updateById(engnTechProj.getData());

        if (engnTechProj.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            engnTechProj.getFiles().forEach(obj -> {
                obj.setObjectId(engnTechProj.getData().getProjectId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    public List<EngnTechProjDTO> list(EngnTechProj engnTechProj) {
        List<EngnTechProjDTO> list = engnTechProjMapper.selectMapperList(engnTechProj);
        return list;
    }

    @Override
    public EngnTechProjDTO selectById(String id) {
        EngnTechProj engnTechProj = engnTechProjMapper.selectById(id);
        EngnTechProjDTO engnTechProjDTO = new EngnTechProjDTO(engnTechProj);
        return engnTechProjDTO;
    }

    @Override
    public EngnTechProjDetailDTO detailById(String id) {
        EngnTechProjDetailDTO result = new EngnTechProjDetailDTO();
        EngnTechProjDTO engnTechProjDTO = engnTechProjMapper.selectDTO(id);
        result.setData(engnTechProjDTO);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(engnTechProjDTO.getProjectId());
        result.setFiles(fileUploadDTOS);
        return result;
    }

    @Override
    public int del(String id) {
        return engnTechProjMapper.deleteById(id);
    }
}

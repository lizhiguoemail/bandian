package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.ResearchProjAddDTO;
import com.lhsz.bandian.jt.DTO.request.ResearchProjDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ResearchProjUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.ResearchProjDTO;
import com.lhsz.bandian.jt.entity.ResearchProj;
import com.lhsz.bandian.jt.mapper.ResearchProjMapper;
import com.lhsz.bandian.jt.service.IAwardsTypeService;
import com.lhsz.bandian.jt.service.IResearchProjService;
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
 * 主持参与科研项目 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class ResearchProjServiceImpl extends ServiceImpl<ResearchProjMapper, ResearchProj> implements IResearchProjService {
    @Autowired
    private ResearchProjMapper ResearchProjMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDictDataService iDictDataService;
    @Autowired
    private IAttachService attachService;
    @Autowired
    private IAwardsTypeService iAwardsTypeService;

    @Override
    public List<ResearchProjDTO> list(ResearchProj researchProj) {
        List<ResearchProjDTO> researchProjDTOS = ResearchProjMapper.selectMapperList(researchProj);
        return researchProjDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(ResearchProjUpdateDTO researchProjUpdateDTO) {
        ResearchProj researchProj = new ResearchProj();
        BeanUtils.copyProperties(researchProjUpdateDTO,researchProj);
        researchProj.setProjectId(researchProjUpdateDTO.getData().getId());
        updateById(researchProj);

        if (researchProjUpdateDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            researchProjUpdateDTO.getFiles().forEach(obj -> {
                obj.setObjectId(researchProjUpdateDTO.getData().getProjectId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(ResearchProjDTO researchProjDTO) {
        ResearchProj researchProj = new ResearchProj();
        BeanUtils.copyProperties(researchProjDTO,researchProj);
        researchProj.setProjectId(researchProjDTO.getId());
        updateById(researchProj);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(ResearchProjAddDTO researchProjAddDTO) {
        String projectId = UUID.randomUUID().toString();
        ResearchProj researchProj = new ResearchProj();
        researchProjAddDTO.getData().setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        researchProjAddDTO.getData().setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(researchProjAddDTO.getData(),researchProj);
        researchProjAddDTO.getData().setId(researchProj.getProjectId());
        save(researchProj);

        if (researchProjAddDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            researchProjAddDTO.getFiles().forEach(obj -> {
                obj.setObjectId(projectId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }

    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(ResearchProjDTO researchProjDTO) {
        ResearchProj researchProj = new ResearchProj();
        researchProjDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        researchProjDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(researchProjDTO,researchProj);
        researchProjDTO.setId(researchProj.getProjectId());

        save(researchProj);
    }

    @Override
    public ResearchProjDetailDTO detailById(String id) {
        ResearchProjDetailDTO researchProjDetailDTO = new ResearchProjDetailDTO();
        ResearchProjDTO researchProj = ResearchProjMapper.selectDTO(id);
        researchProjDetailDTO.setData(researchProj);
        List<FileUploadDTO> fileUploadDTOS = attachService.listByObjectId(researchProj.getProjectId());
        researchProjDetailDTO.setFiles(fileUploadDTOS);
        return researchProjDetailDTO;
    }

    @Override
    public ResearchProjDTO selectById(String id) {
        ResearchProj researchProj = ResearchProjMapper.selectById(id);
        ResearchProjDTO researchProjDTO = new ResearchProjDTO(researchProj);
        return researchProjDTO;
    }

    @Override
    public int del(String id) {
        return ResearchProjMapper.deleteById(id);
    }
}

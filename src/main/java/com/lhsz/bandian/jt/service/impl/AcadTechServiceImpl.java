package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.AcadTechAddDTO;
import com.lhsz.bandian.jt.DTO.request.AcadTechDetailDTO;
import com.lhsz.bandian.jt.DTO.request.AcadTechUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtAcadTechDTO;
import com.lhsz.bandian.jt.entity.AcadTech;
import com.lhsz.bandian.jt.mapper.AcadTechMapper;
import com.lhsz.bandian.jt.service.IAcadTechService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.service.IAttachService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 学术技术兼职 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class AcadTechServiceImpl extends ServiceImpl<AcadTechMapper, AcadTech> implements IAcadTechService {

    private final AcadTechMapper acadTechMapper;

    private final IAttachService attachService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    public AcadTechServiceImpl(AcadTechMapper acadTechMapper, IAttachService attachService) {
        this.acadTechMapper = acadTechMapper;
        this.attachService = attachService;
    }



    @Override
    public List<JtAcadTechDTO> listAcadTech(AcadTech acadTech) {
        List<JtAcadTechDTO> jtAcadTechDTOS = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        if (acadTech.getOrganCorp() != null && !"".equals(acadTech.getOrganCorp().trim())){
            wrapper.like("organ_corp",acadTech.getOrganCorp());
        }
        if (acadTech.getJobTitle() != null && !"".equals(acadTech.getJobTitle().trim())){
            wrapper.like("job_title",acadTech.getJobTitle());
        }
        if (acadTech.getUserId() != null && !"".equals(acadTech.getUserId().trim())){
            wrapper.eq("user_id",acadTech.getUserId());
        }

        List<AcadTech> list = acadTechMapper.selectList(wrapper);
        for (AcadTech acadTech1 : list) {
            JtAcadTechDTO jtAcadTechDTO = new JtAcadTechDTO();
            BeanUtils.copyProperties(acadTech1,jtAcadTechDTO);
            jtAcadTechDTO.setId(acadTech1.getTechId());
            jtAcadTechDTOS.add(jtAcadTechDTO);
        }

        return jtAcadTechDTOS;
    }

    @Override
    public JtAcadTechDTO getAcadTechId(String techId) {
        AcadTech acadTech = acadTechMapper.selectById(techId);
        JtAcadTechDTO jtAcadTechDTO = new JtAcadTechDTO(acadTech);
        return jtAcadTechDTO;
    }

    @Override
    public AcadTechDetailDTO detailById(String techId) {
        AcadTechDetailDTO detailDTO = new AcadTechDetailDTO();
        AcadTech acadTech = acadTechMapper.selectById(techId);
        JtAcadTechDTO jtAcadTechDTO = new JtAcadTechDTO();
        BeanUtils.copyProperties(acadTech,jtAcadTechDTO);
        jtAcadTechDTO.setId(techId);
        detailDTO.setData(jtAcadTechDTO);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(acadTech.getTechId());
        detailDTO.setFiles(fileUploadDTOS);
        return detailDTO;
    }


    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addAcadTechDTO(JtAcadTechDTO jtAcadTechDTO) {
        AcadTech acadTech = new AcadTech();
        BeanUtils.copyProperties(jtAcadTechDTO,acadTech);
        jtAcadTechDTO.setId(acadTech.getTechId());
        save(acadTech);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addAcadTechDTO(AcadTechAddDTO acadTechAddDTO) {
        String techId = UUID.randomUUID().toString();
        acadTechAddDTO.getData().setTechId(techId);
        acadTechAddDTO.getData().setUserId(acadTechAddDTO.getData().getUserId());
        acadTechAddDTO.getData().setBeginDate(acadTechAddDTO.getData().getBeginDate().substring(0,10));
        acadTechAddDTO.getData().setEndDate(acadTechAddDTO.getData().getEndDate().substring(0,10));
        acadTechAddDTO.getData().setIsDeleted(0);
        save(acadTechAddDTO.getData());

        if (acadTechAddDTO.getFiles().size() > 0){
            List<Attach> attachs = new ArrayList<>();
            acadTechAddDTO.getFiles().forEach(obj->{
                obj.setObjectId(techId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }

    }


    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateAcadTechDTO(JtAcadTechDTO jtAcadTechDTO) {
        AcadTech acadTech = new AcadTech();
        BeanUtils.copyProperties(jtAcadTechDTO,acadTech);
        acadTech.setTechId(jtAcadTechDTO.getId());
        updateById(acadTech);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateAcadTechDTO(AcadTechUpdateDTO acadTechUpdateDTO) {
        AcadTech acadTech = new AcadTech();
        BeanUtils.copyProperties(acadTechUpdateDTO,acadTech);
        updateById(acadTechUpdateDTO.getData());
        if (acadTechUpdateDTO.getFiles().size() > 0 ){
            List<Attach> attachs = new ArrayList<>();
            acadTechUpdateDTO.getFiles().forEach(obj ->{
                obj.setObjectId(acadTechUpdateDTO.getData().getTechId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }


    }

    @Override
    public int delectAcadTech(String id) {
        int deleteById = acadTechMapper.deleteById(id);
        return deleteById;
    }
}

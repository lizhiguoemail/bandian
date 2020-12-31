package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.jt.DTO.request.ThesisAddDTO;
import com.lhsz.bandian.jt.DTO.request.ThesisDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ThesisUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.ThesisDTO;
import com.lhsz.bandian.jt.entity.Thesis;
import com.lhsz.bandian.jt.mapper.ThesisMapper;
import com.lhsz.bandian.jt.service.IThesisService;
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
 * 论文 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class ThesisServiceImpl extends ServiceImpl<ThesisMapper, Thesis> implements IThesisService {
    @Autowired
    private ThesisMapper ThesisMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDictDataService iDictDataService;

    @Autowired
    private IAttachService attachService;

    @Override
    public List<ThesisDTO> list(Thesis thesis) {
        List<ThesisDTO> thesisDTOS = ThesisMapper.selectMapperList(thesis);
        return thesisDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(ThesisUpdateDTO thesisUpdateDTO) {
        Thesis thesis = new Thesis();
        BeanUtils.copyProperties(thesisUpdateDTO.getData(),thesis);
        thesis.setThesisId(thesisUpdateDTO.getData().getId());
        updateById(thesis);

        if (thesisUpdateDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            thesisUpdateDTO.getFiles().forEach(obj -> {
                obj.setObjectId(thesisUpdateDTO.getData().getThesisId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(ThesisDTO thesisDTO) {
        Thesis thesis = new Thesis();
        BeanUtils.copyProperties(thesisDTO,thesis);
        thesis.setThesisId(thesisDTO.getId());
        updateById(thesis);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(ThesisAddDTO thesisAddDTO) {
        String thesisId = UUID.randomUUID().toString();
        Thesis thesis = new Thesis();
        thesisAddDTO.getData().setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        thesisAddDTO.getData().setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(thesisAddDTO.getData(),thesis);
        thesisAddDTO.getData().setId(thesis.getThesisId());
        save(thesis);
        if (thesisAddDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            thesisAddDTO.getFiles().forEach(obj -> {
                obj.setObjectId(thesisId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(ThesisDTO thesisDTO) {
        Thesis thesis = new Thesis();
        thesisDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        thesisDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(thesisDTO,thesis);
        thesisDTO.setId(thesis.getThesisId());

        save(thesis);
    }

    @Override
    public ThesisDetailDTO detailById(String id) {
        ThesisDetailDTO thesisDetailDTO = new ThesisDetailDTO();

        ThesisDTO thesis = ThesisMapper.selectDTO(id);
        thesisDetailDTO.setData(thesis);
        List<FileUploadDTO> fileUploadDTOS = attachService.listByObjectId(thesis.getThesisId());
        thesisDetailDTO.setFiles(fileUploadDTOS);
        return thesisDetailDTO;
    }

    @Override
    public ThesisDTO selectById(String id) {
        Thesis thesis = ThesisMapper.selectById(id);
        ThesisDTO thesisDTO = new ThesisDTO(thesis);
        return thesisDTO;
    }

    @Override
    public int del(String id) {
        return ThesisMapper.deleteById(id);
    }

}

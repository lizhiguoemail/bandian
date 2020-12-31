package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.PartyTeachWorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachWorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachWorkUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.PartyTeachWorkDTO;
import com.lhsz.bandian.jt.entity.PartyTeachWork;
import com.lhsz.bandian.jt.mapper.PartyTeachWorkMapper;
import com.lhsz.bandian.jt.service.IPartyTeachWorkService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.service.IAttachService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 党校教学工作 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class PartyTeachWorkServiceImpl extends ServiceImpl<PartyTeachWorkMapper, PartyTeachWork> implements IPartyTeachWorkService {

    private final PartyTeachWorkMapper partyTeachWorkMapper;
    private final IAttachService attachService;


    public PartyTeachWorkServiceImpl(PartyTeachWorkMapper partyTeachWorkMapper, IAttachService attachService) {
        this.partyTeachWorkMapper = partyTeachWorkMapper;
        this.attachService = attachService;
    }

    @Override
    public boolean add(PartyTeachWork partyTeachWork) {
        partyTeachWork.setSchoolYear(partyTeachWork.getSchoolYear().substring(0,4));
        partyTeachWork.setIsDeleted(0);
        boolean saveState = save(partyTeachWork);
        return saveState;
    }

    @Override
    public boolean add(PartyTeachWorkAddDTO partyTeachWork) {
        String teachId = UUID.randomUUID().toString();
        partyTeachWork.getData().setIsDeleted(0);
        partyTeachWork.getData().setSchoolYear(partyTeachWork.getData().getSchoolYear().substring(0,4));
        boolean saveState = save(partyTeachWork.getData());
        if (partyTeachWork.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            partyTeachWork.getFiles().forEach(obj -> {
                obj.setObjectId(teachId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
        return saveState;
    }

    @Override
    public void update(PartyTeachWork partyTeachWork) {
        updateById(partyTeachWork);
    }

    @Override
    public void update(PartyTeachWorkUpdateDTO partyTeachWork) {
        updateById(partyTeachWork.getData());
        if (partyTeachWork.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            partyTeachWork.getFiles().forEach(obj -> {
                obj.setObjectId(partyTeachWork.getData().getTeachId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    public List<PartyTeachWorkDTO> list(PartyTeachWork partyTeachWork) {
        QueryWrapper<PartyTeachWork> partyTeachWorkQueryWrapper = new QueryWrapper<>();
        if (partyTeachWork.getSchoolYear()!=null&&!"".equals(partyTeachWork.getSchoolYear().trim())){
            partyTeachWorkQueryWrapper.like("school_year",partyTeachWork.getSchoolYear());
        }
        if (partyTeachWork.getUserId()!=null&&!"".equals(partyTeachWork.getUserId().trim())){
            partyTeachWorkQueryWrapper.eq("user_id",partyTeachWork.getUserId());
        }

        List<PartyTeachWork> list = partyTeachWorkMapper.selectList(partyTeachWorkQueryWrapper);

        ArrayList<PartyTeachWorkDTO> partyTeachWorkDTOS = new ArrayList<>();
        for (PartyTeachWork partyTeachWork1 : list) {
            PartyTeachWorkDTO partyTeachWorkDTO = new PartyTeachWorkDTO();
            BeanUtils.copyProperties(partyTeachWork1,partyTeachWorkDTO);
            partyTeachWorkDTO.setId(partyTeachWork1.getTeachId());
            partyTeachWorkDTOS.add(partyTeachWorkDTO);
        }
        return partyTeachWorkDTOS;

    }

    @Override
    public PartyTeachWorkDTO selectById(String id) {
        PartyTeachWork partyTeachWork = partyTeachWorkMapper.selectById(id);
        PartyTeachWorkDTO partyTeachWorkDTO = new PartyTeachWorkDTO();
        BeanUtils.copyProperties(partyTeachWork,partyTeachWorkDTO);
        partyTeachWorkDTO.setId(partyTeachWork.getTeachId());
        return partyTeachWorkDTO;
    }

    @Override
    public PartyTeachWorkDetailDTO detailById(String id) {
        PartyTeachWorkDetailDTO result = new PartyTeachWorkDetailDTO();
        PartyTeachWork partyTeachWork = partyTeachWorkMapper.selectById(id);
        PartyTeachWorkDTO partyTeachWorkDTO = new PartyTeachWorkDTO(partyTeachWork);
        result.setData(partyTeachWorkDTO);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(partyTeachWork.getTeachId());
        result.setFiles(fileUploadDTOS);
        return result;

    }

    @Override
    public int del(String id) {
        return partyTeachWorkMapper.deleteById(id);
    }

}

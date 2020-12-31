package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.PartyTeachTeamWorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachTeamWorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachTeamWorkUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.PartyTeachTeamWorkDTO;
import com.lhsz.bandian.jt.entity.PartyTeachTeamWork;
import com.lhsz.bandian.jt.mapper.PartyTeachTeamWorkMapper;
import com.lhsz.bandian.jt.service.IPartyTeachTeamWorkService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.service.IAttachService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 党校教师团队工作 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class PartyTeachTeamWorkServiceImpl extends ServiceImpl<PartyTeachTeamWorkMapper, PartyTeachTeamWork> implements IPartyTeachTeamWorkService {

    private final PartyTeachTeamWorkMapper partyTeachTeamWorkMapper;
    private final IAttachService attachService;

    @Autowired
    public PartyTeachTeamWorkServiceImpl(PartyTeachTeamWorkMapper partyTeachTeamWorkMapper, IAttachService attachService) {
        this.partyTeachTeamWorkMapper = partyTeachTeamWorkMapper;
        this.attachService = attachService;
    }

    @Override
    public boolean add(PartyTeachTeamWork partyTeachTeamWork) {
        partyTeachTeamWork.setBeginDate(partyTeachTeamWork.getBeginDate().substring(0,10));
        partyTeachTeamWork.setEndDate(partyTeachTeamWork.getEndDate().substring(0,10));
        partyTeachTeamWork.setIsDeleted(0);
        boolean saveState = save(partyTeachTeamWork);
        return saveState;
    }

    @Override
    public boolean add(PartyTeachTeamWorkAddDTO partyTeachTeamWork) {
        String teachId = UUID.randomUUID().toString();
        partyTeachTeamWork.getData().setBeginDate(partyTeachTeamWork.getData().getBeginDate().substring(0,10));
        partyTeachTeamWork.getData().setEndDate(partyTeachTeamWork.getData().getEndDate().substring(0,10));
        partyTeachTeamWork.getData().setIsDeleted(0);
        boolean saveState = save(partyTeachTeamWork.getData());
        if (partyTeachTeamWork.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            partyTeachTeamWork.getFiles().forEach(obj -> {
                obj.setObjectId(teachId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
        return saveState;

    }

    @Override
    public void update(PartyTeachTeamWork partyTeachTeamWork) {
        updateById(partyTeachTeamWork);
    }

    @Override
    public void update(PartyTeachTeamWorkUpdateDTO partyTeachTeamWork) {
        updateById(partyTeachTeamWork.getData());
        if (partyTeachTeamWork.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            partyTeachTeamWork.getFiles().forEach(obj -> {
                obj.setObjectId(partyTeachTeamWork.getData().getTeachId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    public List<PartyTeachTeamWorkDTO> list(PartyTeachTeamWork partyTeachTeamWork) {
        QueryWrapper<PartyTeachTeamWork> partyTeachTeamWorkQueryWrapper = new QueryWrapper<>();
        if (partyTeachTeamWork.getCategory()!=null&&!"".equals(partyTeachTeamWork.getCategory().trim())){
            partyTeachTeamWorkQueryWrapper.like("category",partyTeachTeamWork.getCategory());
        }
        if (partyTeachTeamWork.getRemark()!=null&&!"".equals(partyTeachTeamWork.getRemark().trim())){
            partyTeachTeamWorkQueryWrapper.like("remark",partyTeachTeamWork.getRemark());
        }
        if (partyTeachTeamWork.getUserId()!=null&&!"".equals(partyTeachTeamWork.getUserId().trim())){
            partyTeachTeamWorkQueryWrapper.eq("user_id",partyTeachTeamWork.getUserId());
        }

        List<PartyTeachTeamWork> list = partyTeachTeamWorkMapper.selectList(partyTeachTeamWorkQueryWrapper);
        ArrayList<PartyTeachTeamWorkDTO> partyTeachTeamWorkDTOS = new ArrayList<>();
        for (PartyTeachTeamWork partyTeachTeamWork1 : list) {
            PartyTeachTeamWorkDTO partyTeachTeamWorkDTO = new PartyTeachTeamWorkDTO();
            BeanUtils.copyProperties(partyTeachTeamWork1,partyTeachTeamWorkDTO);
            partyTeachTeamWorkDTO.setId(partyTeachTeamWork1.getTeachId());
            partyTeachTeamWorkDTOS.add(partyTeachTeamWorkDTO);
        }
        return partyTeachTeamWorkDTOS;

    }

    @Override
    public PartyTeachTeamWorkDTO selectById(String id) {
        PartyTeachTeamWork partyTeachTeamWork = partyTeachTeamWorkMapper.selectById(id);
        PartyTeachTeamWorkDTO partyTeachTeamWorkDTO = new PartyTeachTeamWorkDTO();
        BeanUtils.copyProperties(partyTeachTeamWork,partyTeachTeamWorkDTO);
        partyTeachTeamWorkDTO.setId(partyTeachTeamWork.getTeachId());
        return partyTeachTeamWorkDTO;
    }

    @Override
    public PartyTeachTeamWorkDetailDTO detailById(String id) {
        PartyTeachTeamWorkDetailDTO result = new PartyTeachTeamWorkDetailDTO();
        PartyTeachTeamWork partyTeachTeamWork = partyTeachTeamWorkMapper.selectById(id);
        PartyTeachTeamWorkDTO partyTeachTeamWorkDTO = new PartyTeachTeamWorkDTO(partyTeachTeamWork);
        result.setData(partyTeachTeamWorkDTO);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(partyTeachTeamWork.getTeachId());
        result.setFiles(fileUploadDTOS);
        return result;

    }

    @Override
    public int del(String id) {
        return partyTeachTeamWorkMapper.deleteById(id);
    }

}

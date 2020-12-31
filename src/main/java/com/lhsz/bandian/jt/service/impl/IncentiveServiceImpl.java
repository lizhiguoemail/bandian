package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.IncentiveAddDTO;
import com.lhsz.bandian.jt.DTO.request.IncentiveDetailDTO;
import com.lhsz.bandian.jt.DTO.request.IncentiveUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.IncentiveDTO;
import com.lhsz.bandian.jt.entity.Incentive;
import com.lhsz.bandian.jt.mapper.IncentiveMapper;
import com.lhsz.bandian.jt.service.IIncentiveService;
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
 * 奖惩情况 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class IncentiveServiceImpl extends ServiceImpl<IncentiveMapper, Incentive> implements IIncentiveService {

    private final IncentiveMapper incentiveMapper;
    private final IAttachService attachService;

    @Autowired
    public IncentiveServiceImpl(IncentiveMapper incentiveMapper, IAttachService attachService) {
        this.incentiveMapper = incentiveMapper;
        this.attachService = attachService;
    }


    @Override
    public boolean add(IncentiveAddDTO incentive) {
        String incentiveId = UUID.randomUUID().toString();
        incentive.getData().setIncentiveDate(incentive.getData().getIncentiveDate().substring(0,10));
        incentive.getData().setIsDeleted(0);
        boolean saveState = save(incentive.getData());
        if (incentive.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            incentive.getFiles().forEach(obj -> {
                obj.setObjectId(incentiveId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
        return saveState;

    }


    @Override
    public boolean add(Incentive incentive) {
        incentive.setIncentiveDate(incentive.getIncentiveDate().substring(0,10));
        incentive.setIsDeleted(0);
        boolean saveState = save(incentive);
        return saveState;
    }



    @Override
    public void update(IncentiveUpdateDTO incentive) {
        updateById(incentive.getData());
        if (incentive.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            incentive.getFiles().forEach(obj -> {
                obj.setObjectId(incentive.getData().getIncentiveId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    public void update(Incentive incentive) {
        updateById(incentive);
    }

    @Override
    public List<IncentiveDTO> list(Incentive incentive) {
        List<IncentiveDTO> list = incentiveMapper.selectMapperList(incentive);
        return list;

    }

    @Override
    public IncentiveDTO selectById(String id) {
        Incentive incentive = incentiveMapper.selectById(id);
        IncentiveDTO incentiveDTO = new IncentiveDTO(incentive);
        return incentiveDTO;
    }

    @Override
    public IncentiveDetailDTO detailById(String id) {
        IncentiveDetailDTO result = new IncentiveDetailDTO();
        IncentiveDTO incentiveDTO = incentiveMapper.selectDTO(id);
        result.setData(incentiveDTO);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(incentiveDTO.getIncentiveId());
        result.setFiles(fileUploadDTOS);
        return result;
    }

    @Override
    public int del(String id) {
        return incentiveMapper.deleteById(id);
    }

}

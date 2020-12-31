package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.MakeStandardAddDTO;
import com.lhsz.bandian.jt.DTO.request.MakeStandardDetailDTO;
import com.lhsz.bandian.jt.DTO.request.MakeStandardUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.MakeStandardDTO;
import com.lhsz.bandian.jt.entity.MakeStandard;
import com.lhsz.bandian.jt.mapper.MakeStandardMapper;
import com.lhsz.bandian.jt.service.IMakeStandardService;
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
 * 主持(参与)制定标准 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class MakeStandardServiceImpl extends ServiceImpl<MakeStandardMapper, MakeStandard> implements IMakeStandardService {

    private final MakeStandardMapper makeStandardMapper;
    private final IAttachService attachService;

    @Autowired
    public MakeStandardServiceImpl(MakeStandardMapper makeStandardMapper, IAttachService attachService) {
        this.makeStandardMapper = makeStandardMapper;
        this.attachService = attachService;
    }

    @Override
    public boolean add(MakeStandard makeStandard) {
        makeStandard.setPublishedDate(makeStandard.getPublishedDate().substring(0,10));
        makeStandard.setIsDeleted(0);
        boolean saveState = save(makeStandard);
        return saveState;
    }

    @Override
    public boolean add(MakeStandardAddDTO makeStandard) {
        String standardId = UUID.randomUUID().toString();
        makeStandard.getData().setPublishedDate(makeStandard.getData().getPublishedDate().substring(0,10));
        makeStandard.getData().setIsDeleted(0);
        boolean saveState = save(makeStandard.getData());
        if (makeStandard.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            makeStandard.getFiles().forEach(obj -> {
                obj.setObjectId(standardId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
        return saveState;


    }

    @Override
    public void update(MakeStandard makeStandard) {
        updateById(makeStandard);
    }

    @Override
    public void update(MakeStandardUpdateDTO makeStandard) {
        updateById(makeStandard.getData());
        if (makeStandard.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            makeStandard.getFiles().forEach(obj -> {
                obj.setObjectId(makeStandard.getData().getStandardId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }

    }

    @Override
    public List<MakeStandardDTO> list(MakeStandard makeStandard) {

        List<MakeStandardDTO> list = makeStandardMapper.selectMapperList(makeStandard);
        return list;

    }

    @Override
    public MakeStandardDTO selectById(String id) {
        MakeStandard makeStandard = makeStandardMapper.selectById(id);
        MakeStandardDTO makeStandardDTO = new MakeStandardDTO(makeStandard);
        return makeStandardDTO;
    }

    @Override
    public MakeStandardDetailDTO detailById(String id) {
        MakeStandardDetailDTO result = new MakeStandardDetailDTO();
        MakeStandardDTO makeStandardDTO = makeStandardMapper.selectDTO(id);
        result.setData(makeStandardDTO);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(makeStandardDTO.getStandardId());
        result.setFiles(fileUploadDTOS);
        return result;
    }

    @Override
    public int del(String id) {
        return makeStandardMapper.deleteById(id);
    }

}

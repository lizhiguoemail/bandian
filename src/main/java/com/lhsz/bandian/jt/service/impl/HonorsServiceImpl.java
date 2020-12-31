package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.HonorsAddDTO;
import com.lhsz.bandian.jt.DTO.request.HonorsDetailDTO;
import com.lhsz.bandian.jt.DTO.request.HonorsUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.HonorsDTO;
import com.lhsz.bandian.jt.entity.Honors;
import com.lhsz.bandian.jt.mapper.HonorsMapper;
import com.lhsz.bandian.jt.service.IHonorsService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.service.IAttachService;
import com.lhsz.bandian.sys.service.IDictDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 获得荣誉 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class HonorsServiceImpl extends ServiceImpl<HonorsMapper, Honors> implements IHonorsService {

    private final HonorsMapper honorsMapper;
    private final IDictDataService iDictDataService;
    private final IAttachService attachService;


    @Autowired
    public HonorsServiceImpl(HonorsMapper honorsMapper , IDictDataService iDictDataService, IAttachService attachService) {
        this.honorsMapper = honorsMapper;
        this.iDictDataService = iDictDataService;
        this.attachService = attachService;
    }


    @Override
    public boolean add(Honors honors) {
        honors.setGrantDate(honors.getGrantDate().substring(0,10));
        honors.setIsDeleted(0);
        boolean saveState = save(honors);
        return saveState;
    }

    @Override
    public boolean add(HonorsAddDTO honors) {
        String honorId = UUID.randomUUID().toString();
        honors.getData().setHonorId(honorId);
        honors.getData().setGrantDate(honors.getData().getGrantDate().substring(0,10));
        honors.getData().setIsDeleted(0);
        boolean saveState = save(honors.getData());
        if (honors.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            honors.getFiles().forEach(obj -> {
                obj.setObjectId(honorId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }

        return saveState;
    }


    @Override
    public void update(HonorsDTO honorsDTO) {
        honorsDTO.setGrantDate(honorsDTO.getGrantDate().substring(0,10));
        Honors honors = new Honors();
        BeanUtils.copyProperties(honorsDTO,honors);
        honors.setHonorId(honorsDTO.getId());
        updateById(honors);
    }

    @Override
    public void update(HonorsUpdateDTO honorsDTO) {
        honorsDTO.getData().setGrantDate(honorsDTO.getData().getGrantDate().substring(0,10));
        updateById(honorsDTO.getData());

        if (honorsDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            honorsDTO.getFiles().forEach(obj -> {
                obj.setObjectId(honorsDTO.getData().getHonorId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }

    }

    @Override
    public List<HonorsDTO> list(Honors honors) {
        List<HonorsDTO> honorsDTOList = honorsMapper.selectMapperList(honors);
        return honorsDTOList;
    }

    @Override
    public HonorsDTO selectById(String id) {
        Honors honors = honorsMapper.selectById(id);
        HonorsDTO honorsDTO = new HonorsDTO(honors);
        return honorsDTO;
    }

    @Override
    public HonorsDetailDTO detailById(String id) {
        HonorsDetailDTO result = new HonorsDetailDTO();
        HonorsDTO honorsDTO = honorsMapper.selectDTO(id);
        result.setData(honorsDTO);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(honorsDTO.getHonorId());
        result.setFiles(fileUploadDTOS);
        return result;
    }

    @Override
    public int del(String id) {
        return honorsMapper.deleteById(id);
    }

}

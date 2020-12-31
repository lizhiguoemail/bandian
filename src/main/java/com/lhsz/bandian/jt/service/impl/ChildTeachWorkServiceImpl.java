package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.ChildTeachWorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachWorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachWorkUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtChildTeachWorkDTO;
import com.lhsz.bandian.jt.entity.ChildTeachWork;
import com.lhsz.bandian.jt.mapper.ChildTeachWorkMapper;
import com.lhsz.bandian.jt.service.IChildTeachWorkService;
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
 * 中小学幼儿老师日常教学 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class ChildTeachWorkServiceImpl extends ServiceImpl<ChildTeachWorkMapper, ChildTeachWork> implements IChildTeachWorkService {

    @Autowired
    private ChildTeachWorkMapper childTeachWorkMapper;

    @Autowired
    private IDictDataService iDictDataService;

    @Autowired
    private IAttachService attachService;

    @Override
    public List<JtChildTeachWorkDTO> listChildTeachWork(ChildTeachWork childTeachWork) {
        List<JtChildTeachWorkDTO> jtChildTeachWorkDTOS = childTeachWorkMapper.selectMapperList(childTeachWork);
        return jtChildTeachWorkDTOS;
    }

    @Override
    public JtChildTeachWorkDTO getChildTeachWork(String id) {
        ChildTeachWork childTeachWork = childTeachWorkMapper.selectById(id);
        JtChildTeachWorkDTO jtChildTeachWorkDTO = new JtChildTeachWorkDTO();
        BeanUtils.copyProperties(childTeachWork,jtChildTeachWorkDTO);
        jtChildTeachWorkDTO.setId(childTeachWork.getTeachId());
        return jtChildTeachWorkDTO;
    }

    @Override
    public ChildTeachWorkDetailDTO detailById(String id) {
        ChildTeachWorkDetailDTO detailDTO = new ChildTeachWorkDetailDTO();
        JtChildTeachWorkDTO childTeachWork = childTeachWorkMapper.selectDTO(id);
        detailDTO.setData(childTeachWork);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(childTeachWork.getTeachId());
        detailDTO.setFiles(fileUploadDTOS);
        return detailDTO;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addChildTeachWork(JtChildTeachWorkDTO jtChildTeachWorkDTO) {
        ChildTeachWork childTeachWork = new ChildTeachWork();
        BeanUtils.copyProperties(jtChildTeachWorkDTO,childTeachWork);
        jtChildTeachWorkDTO.setId(childTeachWork.getTeachId());
        save(childTeachWork);
    }

    @Override
    public void addChildTeachWork(ChildTeachWorkAddDTO jtChildTeachWorkDTO) {
        String Id = UUID.randomUUID().toString();
        jtChildTeachWorkDTO.getData().setTeachId(Id);
        save(jtChildTeachWorkDTO.getData());
        if (jtChildTeachWorkDTO.getFiles().size() > 0 ){
            List<Attach> attachs = new ArrayList<>();
            jtChildTeachWorkDTO.getFiles().forEach(obj ->{
                obj.setObjectId(Id);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateChildTeachWork(JtChildTeachWorkDTO jtChildTeachWorkDTO) {
        ChildTeachWork childTeachWork = new ChildTeachWork();
        BeanUtils.copyProperties(jtChildTeachWorkDTO,childTeachWork);
        childTeachWork.setTeachId(jtChildTeachWorkDTO.getId());
        updateById(childTeachWork);
    }

    @Override
    public void updateChildTeachWork(ChildTeachWorkUpdateDTO jtChildTeachWorkDTO) {
        updateById(jtChildTeachWorkDTO.getData());
        if (jtChildTeachWorkDTO.getFiles().size() > 0 ){
            List<Attach> attachs = new ArrayList<>();
            jtChildTeachWorkDTO.getFiles().forEach(obj ->{
                obj.setObjectId(jtChildTeachWorkDTO.getData().getTeachId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }

    }

    @Override
    public int delChildTeachWork(String id) {
        int deleteById = childTeachWorkMapper.deleteById(id);
        return deleteById;
    }
}

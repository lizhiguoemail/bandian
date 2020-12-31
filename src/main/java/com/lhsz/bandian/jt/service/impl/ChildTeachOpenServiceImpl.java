package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.ChildTeachOpenAddDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachOpenDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachOpenUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtChildTeachOpenDTO;
import com.lhsz.bandian.jt.entity.ChildTeachOpen;
import com.lhsz.bandian.jt.mapper.ChildTeachOpenMapper;
import com.lhsz.bandian.jt.service.IChildTeachOpenService;
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
 * 中小学幼儿老师公开课 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class ChildTeachOpenServiceImpl extends ServiceImpl<ChildTeachOpenMapper, ChildTeachOpen> implements IChildTeachOpenService {

    @Autowired
    private ChildTeachOpenMapper childTeachOpenMapper;

    @Autowired
    private IAttachService attachService;

    @Override
    public List<JtChildTeachOpenDTO> listChildTeachOpen(ChildTeachOpen childTeachOpen) {
        List<JtChildTeachOpenDTO> jtChildTeachOpenDTOS = new ArrayList<>();
        QueryWrapper<ChildTeachOpen> queryWrapper = new QueryWrapper<>();
        if (childTeachOpen.getCourseName() != null && !"".equals(childTeachOpen.getCourseName().trim())) {
            queryWrapper.like("course_name",childTeachOpen.getCourseName());
        }
        if (childTeachOpen.getOrganName() != null && !"".equals(childTeachOpen.getOrganName().trim())) {
            queryWrapper.like("organ_name",childTeachOpen.getOrganName());
        }
        if (childTeachOpen.getUserId() != null && !"".equals(childTeachOpen.getUserId().trim())) {
            queryWrapper.eq("user_id",childTeachOpen.getUserId());
        }

        List<ChildTeachOpen> awardsList = childTeachOpenMapper.selectList(queryWrapper);
        for (ChildTeachOpen awards1:awardsList) {
            JtChildTeachOpenDTO jtChildTeachOpenDTO = new JtChildTeachOpenDTO();
            BeanUtils.copyProperties(awards1,jtChildTeachOpenDTO);
            jtChildTeachOpenDTO.setId(awards1.getTeachId());
            jtChildTeachOpenDTOS.add(jtChildTeachOpenDTO);
        }
        return jtChildTeachOpenDTOS;
    }

    @Override
    public JtChildTeachOpenDTO getChildTeachOpen(String id) {
        ChildTeachOpen childTeachOpen = childTeachOpenMapper.selectById(id);
        JtChildTeachOpenDTO jtChildTeachOpenDTO = new JtChildTeachOpenDTO();
        BeanUtils.copyProperties(childTeachOpen,jtChildTeachOpenDTO);
        jtChildTeachOpenDTO.setId(id);
        return jtChildTeachOpenDTO;
    }

    @Override
    public ChildTeachOpenDetailDTO detailById(String id) {
        ChildTeachOpenDetailDTO detailDTO = new ChildTeachOpenDetailDTO();
        ChildTeachOpen childTeachOpen = childTeachOpenMapper.selectById(id);
        JtChildTeachOpenDTO jtChildTeachOpenDTO = new JtChildTeachOpenDTO();
        BeanUtils.copyProperties(childTeachOpen,jtChildTeachOpenDTO);
        jtChildTeachOpenDTO.setId(id);
        detailDTO.setData(jtChildTeachOpenDTO);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(childTeachOpen.getTeachId());
        detailDTO.setFiles(fileUploadDTOS);
        return detailDTO;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addChildTeachOpen(JtChildTeachOpenDTO jtChildTeachOpenDTO) {
        ChildTeachOpen childTeachOpen = new ChildTeachOpen();
        BeanUtils.copyProperties(jtChildTeachOpenDTO,childTeachOpen);
        jtChildTeachOpenDTO.setId(childTeachOpen.getTeachId());
        save(childTeachOpen);
    }

    @Override
    public void addChildTeachOpen(ChildTeachOpenAddDTO jtChildTeachOpenDTO) {
        String Id = UUID.randomUUID().toString();
        jtChildTeachOpenDTO.getData().setTeachId(Id);
        save(jtChildTeachOpenDTO.getData());
        if (jtChildTeachOpenDTO.getFiles().size() > 0 ){
            List<Attach> attachs = new ArrayList<>();
            jtChildTeachOpenDTO.getFiles().forEach(obj ->{
                obj.setObjectId(Id);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateChildTeachOpen(JtChildTeachOpenDTO jtChildTeachOpenDTO) {
        ChildTeachOpen childTeachOpen = new ChildTeachOpen();
        BeanUtils.copyProperties(jtChildTeachOpenDTO,childTeachOpen);
        childTeachOpen.setTeachId(jtChildTeachOpenDTO.getId());
        updateById(childTeachOpen);
    }

    @Override
    public void updateChildTeachOpen(ChildTeachOpenUpdateDTO jtChildTeachOpenDTO) {
        updateById(jtChildTeachOpenDTO.getData());
        if (jtChildTeachOpenDTO.getFiles().size() > 0 ){
            List<Attach> attachs = new ArrayList<>();
            jtChildTeachOpenDTO.getFiles().forEach(obj ->{
                obj.setObjectId(jtChildTeachOpenDTO.getData().getTeachId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    public int delChildTeachOpen(String id) {
        return childTeachOpenMapper.deleteById(id);
    }
}

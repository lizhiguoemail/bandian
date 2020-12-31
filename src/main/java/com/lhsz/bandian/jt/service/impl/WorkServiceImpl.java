package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.jt.DTO.request.WorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.WorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.WorkUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.WorkDTO;
import com.lhsz.bandian.jt.entity.Work;
import com.lhsz.bandian.jt.mapper.WorkMapper;
import com.lhsz.bandian.jt.service.IWorkService;
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
 * 工作经历 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements IWorkService {
    @Autowired
    private com.lhsz.bandian.jt.mapper.WorkMapper WorkMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDictDataService iDictDataService;
    @Autowired
    private IAttachService attachService;
    @Override
    public List<WorkDTO> list(Work work) {
        List<WorkDTO> workDTOS = WorkMapper.selectMapperList(work);
        return workDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(WorkDTO workDTO) {
        Work work = new Work();
        BeanUtils.copyProperties(workDTO,work);
        work.setWorkId(workDTO.getId());
        updateById(work);
    }

    @Override
    public void update(WorkUpdateDTO workDTO) {
        updateById(workDTO.getData());
        if (workDTO.getFiles().size() > 0){
            List<Attach> attachs = new ArrayList<>();
            workDTO.getFiles().forEach(obj->{
                obj.setObjectId(workDTO.getData().getWorkId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }

    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(WorkDTO workDTO) {
        Work work = new Work();
        workDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        workDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(workDTO,work);
        workDTO.setId(work.getWorkId());
        save(work);
    }

    @Override
    public void add(WorkAddDTO workDTO) {
        String techId = UUID.randomUUID().toString();
        Work work = new Work();
        workDTO.getData().setWorkId(techId);
        workDTO.getData().setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        workDTO.getData().setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(workDTO,work);
        save(workDTO.getData());

        if (workDTO.getFiles().size() > 0){
            List<Attach> attachs = new ArrayList<>();
            workDTO.getFiles().forEach(obj->{
                obj.setObjectId(techId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    public WorkDTO selectById(String id) {
        Work work = WorkMapper.selectById(id);
        WorkDTO workDTO = new WorkDTO(work);
        return workDTO;
    }

    @Override
    public WorkDetailDTO detailById(String id) {
        WorkDetailDTO detailDTO = new WorkDetailDTO();
        WorkDTO work = WorkMapper.selectDTO(id);
        detailDTO.setData(work);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(work.getWorkId());
        detailDTO.setFiles(fileUploadDTOS);
        return detailDTO;
    }

    @Override
    public int del(String id) {
        return WorkMapper.deleteById(id);
    }
}

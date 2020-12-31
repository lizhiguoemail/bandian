package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.PatentAddDTO;
import com.lhsz.bandian.jt.DTO.request.PatentDetailDTO;
import com.lhsz.bandian.jt.DTO.request.PatentUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.PatentDTO;
import com.lhsz.bandian.jt.entity.Patent;
import com.lhsz.bandian.jt.mapper.PatentMapper;
import com.lhsz.bandian.jt.service.IPatentService;
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
 * 专利(著作权) 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class PatentServiceImpl extends ServiceImpl<PatentMapper, Patent> implements IPatentService {

    @Autowired
    private PatentMapper patentMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IDictDataService iDictDataService;

    @Autowired
    private IAttachService attachService;

    @Override
    public List<PatentDTO> list(Patent patent) {
        List<PatentDTO> patentDTOS = patentMapper.selectMapperList(patent);
        return patentDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(PatentUpdateDTO patentUpdateDTO) {

        Patent patent = new Patent();
        BeanUtils.copyProperties(patentUpdateDTO.getData(),patent);
        patent.setPatentId(patentUpdateDTO.getData().getId());
        updateById(patent);

        if (patentUpdateDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            patentUpdateDTO.getFiles().forEach(obj -> {
                obj.setObjectId(patentUpdateDTO.getData().getPatentId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(PatentDTO patentDTO) {
        Patent patent = new Patent();
        BeanUtils.copyProperties(patentDTO,patent);
        patent.setPatentId(patentDTO.getId());
        updateById(patent);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(PatentAddDTO patentAddDTO) {
        String patentId = UUID.randomUUID().toString();
        Patent patent = new Patent();
        patentAddDTO.getData().setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        patentAddDTO.getData().setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(patentAddDTO.getData(),patent);
        patentAddDTO.getData().setId(patent.getPatentId());
        save(patent);
        if (patentAddDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            patentAddDTO.getFiles().forEach(obj -> {
                obj.setObjectId(patentId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }

    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(PatentDTO patentDTO) {
        Patent patent = new Patent();
        patentDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        patentDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(patentDTO,patent);
        patentDTO.setId(patent.getPatentId());
        save(patent);
    }

    @Override
    public PatentDTO selectById(String id) {
        Patent patent = patentMapper.selectById(id);
        PatentDTO patentDTO = new PatentDTO(patent);
        return patentDTO;
    }

    @Override
    public PatentDetailDTO detailById(String id) {
        PatentDetailDTO patentDetailDTO = new PatentDetailDTO();
        PatentDTO patent = patentMapper.selectDTO(id);
        patentDetailDTO.setData(patent);
        List<FileUploadDTO> fileUploadDTOS = attachService.listByObjectId(patent.getPatentId());
        patentDetailDTO.setFiles(fileUploadDTOS);
        return patentDetailDTO;
    }

    @Override
    public int del(String id) {
        return patentMapper.deleteById(id);
    }
}

package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.jt.DTO.request.WritingsAddDTO;
import com.lhsz.bandian.jt.DTO.request.WritingsDetailDTO;
import com.lhsz.bandian.jt.DTO.request.WritingsUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.WritingsDTO;
import com.lhsz.bandian.jt.entity.Writings;
import com.lhsz.bandian.jt.mapper.WritingsMapper;
import com.lhsz.bandian.jt.service.IWritingsService;
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

/**
 * <p>
 * 著(译)作(教材) 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class WritingsServiceImpl extends ServiceImpl<WritingsMapper, Writings> implements IWritingsService {
    @Autowired
    private com.lhsz.bandian.jt.mapper.WritingsMapper WritingsMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDictDataService iDictDataService;
    @Autowired
    private IAttachService attachService;
    @Override
    public List<WritingsDTO> list(Writings writings) {
        List<WritingsDTO> writingsDTOS = WritingsMapper.selectMapperList(writings);
        return writingsDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(WritingsDTO writingsDTO) {
        Writings writings = new Writings();
        BeanUtils.copyProperties(writingsDTO,writings);
        writings.setWritingId(writingsDTO.getId());
        updateById(writings);
    }

    @Override
    public void update(WritingsUpdateDTO writingsDTO) {
        updateById(writingsDTO.getData());
        if (writingsDTO.getFiles().size() > 0){
            List<Attach> attachs = new ArrayList<>();
            writingsDTO.getFiles().forEach(obj->{
                obj.setObjectId(writingsDTO.getData().getWritingId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }

    }


    @Override
    public void add(WritingsAddDTO writingsDTO) {
        Writings writings = new Writings();
        writingsDTO.getData().setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        writingsDTO.getData().setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(writingsDTO,writings);
        save(writingsDTO.getData());
        if (writingsDTO.getFiles().size() > 0){
            List<Attach> attachs = new ArrayList<>();
            writingsDTO.getFiles().forEach(obj->{
                obj.setObjectId(writingsDTO.getData().getWritingId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(WritingsDTO writingsDTO) {
        Writings writings = new Writings();
        writingsDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        writingsDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(writingsDTO,writings);
        writingsDTO.setId(writings.getWritingId());

        save(writings);
    }

    @Override
    public WritingsDTO selectById(String id) {
        Writings writings = WritingsMapper.selectById(id);
        WritingsDTO writingsDTO = new WritingsDTO(writings);
        return writingsDTO;
    }

    @Override
    public WritingsDetailDTO detailById(String id) {
        WritingsDetailDTO detailDTO = new WritingsDetailDTO();
        WritingsDTO writings = WritingsMapper.selectDTO(id);
        detailDTO.setData(writings);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(writings.getWritingId());
        detailDTO.setFiles(fileUploadDTOS);
        return detailDTO;
    }


    @Override
    public int del(String id) {
        return WritingsMapper.deleteById(id);
    }
}

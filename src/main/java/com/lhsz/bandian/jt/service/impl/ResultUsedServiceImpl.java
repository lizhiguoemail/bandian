package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.jt.DTO.request.ResultUsedAddDTO;
import com.lhsz.bandian.jt.DTO.request.ResultUsedDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ResultUsedUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.ResultUsedDTO;
import com.lhsz.bandian.jt.entity.ResultUsed;
import com.lhsz.bandian.jt.mapper.ResultUsedMapper;
import com.lhsz.bandian.jt.service.IResultUsedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.entity.User;
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
 * 成果被批示、采纳、运用和推广 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class ResultUsedServiceImpl extends ServiceImpl<ResultUsedMapper, ResultUsed> implements IResultUsedService {
    @Autowired
    private ResultUsedMapper resultUsedMapper;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private IAttachService attachService;
    @Override
    public List<ResultUsedDTO> list(ResultUsed resultUsed) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            resultUsed.setUserId(user.getUserId());
        }
        resultUsed.setUserId(resultUsed.getUserId());
        ArrayList<ResultUsedDTO> resultUsedDTOS = new ArrayList<>();
        QueryWrapper<ResultUsed> resultUsedQueryWrapper = new QueryWrapper<>();
        if (resultUsed.getResultName()!=null&&!"".equals(resultUsed.getResultName().trim())){
            resultUsedQueryWrapper.like("result_name",resultUsed.getResultName());
        }
        if (resultUsed.getProjectDate()!=null){
            resultUsedQueryWrapper.eq("project_date",resultUsed.getProjectDate());
        }
        if (resultUsed.getUserId()!=null&&!"".equals(resultUsed.getUserId().trim())){
            resultUsedQueryWrapper.eq("user_id",resultUsed.getUserId());
        }

        List<ResultUsed> list = resultUsedMapper.selectList(resultUsedQueryWrapper);
        for (ResultUsed resultUsed1 : list) {
            ResultUsedDTO resultUsedDTO = new ResultUsedDTO(resultUsed1);
            resultUsedDTOS.add(resultUsedDTO);
        }
        return resultUsedDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(ResultUsedUpdateDTO resultUsedUpdateDTO) {
        ResultUsed resultUsed = new ResultUsed();
        BeanUtils.copyProperties(resultUsedUpdateDTO.getData(),resultUsed);
        resultUsed.setResultId(resultUsedUpdateDTO.getData().getId());
        updateById(resultUsed);

        if (resultUsedUpdateDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            resultUsedUpdateDTO.getFiles().forEach(obj -> {
                obj.setObjectId(resultUsedUpdateDTO.getData().getResultId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(ResultUsedDTO resultUsedDTO) {
        ResultUsed resultUsed = new ResultUsed();
        BeanUtils.copyProperties(resultUsedDTO,resultUsed);
        resultUsed.setResultId(resultUsedDTO.getId());
        updateById(resultUsed);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(ResultUsedAddDTO resultUsedAddDTO) {
        String resultId = UUID.randomUUID().toString();
        ResultUsed resultUsed = new ResultUsed();
        resultUsedAddDTO.getData().setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        resultUsedAddDTO.getData().setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(resultUsedAddDTO.getData(),resultUsed);
        resultUsedAddDTO.getData().setId(resultUsed.getResultId());
        save(resultUsed);
        if (resultUsedAddDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            resultUsedAddDTO.getFiles().forEach(obj -> {
                obj.setObjectId(resultId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(ResultUsedDTO resultUsedDTO) {
        ResultUsed resultUsed = new ResultUsed();
        resultUsedDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        resultUsedDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(resultUsedDTO,resultUsed);
        resultUsedDTO.setId(resultUsed.getResultId());

        save(resultUsed);
    }

    @Override
    public ResultUsedDetailDTO detailById(String id) {
        ResultUsedDetailDTO resultUsedDetailDTO = new ResultUsedDetailDTO();
        ResultUsed resultUsed = resultUsedMapper.selectById(id);
        ResultUsedDTO resultUsedDTO = new ResultUsedDTO(resultUsed);
        resultUsedDetailDTO.setData(resultUsedDTO);
        List<FileUploadDTO> fileUploadDTOS = attachService.listByObjectId(resultUsed.getResultId());
        resultUsedDetailDTO.setFiles(fileUploadDTOS);
        return resultUsedDetailDTO;
    }

    @Override
    public ResultUsedDTO selectById(String id) {
        ResultUsed resultUsed = resultUsedMapper.selectById(id);
        ResultUsedDTO resultUsedDTO = new ResultUsedDTO(resultUsed);
        return resultUsedDTO;
    }

    @Override
    public int del(String id) {
        return resultUsedMapper.deleteById(id);
    }
}

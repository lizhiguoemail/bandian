package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.DTO.request.QaCertAddDTO;
import com.lhsz.bandian.jt.DTO.request.QaCertDetailDTO;
import com.lhsz.bandian.jt.DTO.request.QaCertUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.QaCertDTO;
import com.lhsz.bandian.jt.entity.QaCert;
import com.lhsz.bandian.jt.mapper.QaCertMapper;
import com.lhsz.bandian.jt.service.IQaCertService;
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
 * 资质证书 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class QaCertServiceImpl extends ServiceImpl<QaCertMapper, QaCert> implements IQaCertService {
    @Autowired
    private QaCertMapper qaCertMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IAttachService attachService;

    @Override
    public List<QaCertDTO> list(QaCert qaCert) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            qaCert.setUserId(user.getUserId());
        }
        qaCert.setUserId(qaCert.getUserId());
        ArrayList<QaCertDTO> qaCertDTOS = new ArrayList<>();
        QueryWrapper<QaCert> qaCertQueryWrapper = new QueryWrapper<>();
        if (qaCert.getSpecialty() != null && !"".equals(qaCert.getSpecialty().trim())) {
            qaCertQueryWrapper.like("specialty", qaCert.getSpecialty());
        }
        if (qaCert.getCertName() != null && !"".equals(qaCert.getCertName().trim())) {
            qaCertQueryWrapper.like("cert_name", qaCert.getCertName());
        }
        if (qaCert.getUserId() != null && !"".equals(qaCert.getUserId().trim())) {
            qaCertQueryWrapper.eq("user_id", qaCert.getUserId());
        }

        List<QaCert> list = qaCertMapper.selectList(qaCertQueryWrapper);
        for (QaCert qaCert1 : list) {
            QaCertDTO qaCertDTO = new QaCertDTO(qaCert1);
            qaCertDTOS.add(qaCertDTO);
        }
        return qaCertDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(QaCertUpdateDTO qaCertUpdateDTO) {
        QaCert QaCert = new QaCert();
        BeanUtils.copyProperties(qaCertUpdateDTO.getData(), QaCert);
        QaCert.setCertId(qaCertUpdateDTO.getData().getId());
        updateById(QaCert);

        if (qaCertUpdateDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            qaCertUpdateDTO.getFiles().forEach(obj -> {
                obj.setObjectId(qaCertUpdateDTO.getData().getCertId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(QaCertDTO qaCertDTO) {
        QaCert QaCert = new QaCert();
        BeanUtils.copyProperties(qaCertDTO, QaCert);
        QaCert.setCertId(qaCertDTO.getId());
        updateById(QaCert);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(QaCertAddDTO qaCertAddDTO) {
        String certId = UUID.randomUUID().toString();
        QaCert QaCert = new QaCert();
        qaCertAddDTO.getData().setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        qaCertAddDTO.getData().setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(qaCertAddDTO.getData(), QaCert);
        qaCertAddDTO.getData().setId(QaCert.getCertId());
        save(QaCert);
        if (qaCertAddDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            qaCertAddDTO.getFiles().forEach(obj -> {
                obj.setObjectId(certId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }

    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(QaCertDTO qaCertDTO) {
        QaCert QaCert = new QaCert();
        qaCertDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        qaCertDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(qaCertDTO, QaCert);
        qaCertDTO.setId(QaCert.getCertId());

        save(QaCert);
    }

    @Override
    public QaCertDetailDTO detailById(String id) {
        QaCertDetailDTO qaCertDetailDTO = new QaCertDetailDTO();
        QaCert qaCert = qaCertMapper.selectById(id);
        QaCertDTO qaCertDTO = new QaCertDTO(qaCert);
        qaCertDetailDTO.setData(qaCertDTO);

        List<FileUploadDTO> fileUploadDTOS = attachService.listByObjectId(qaCert.getCertId());
        qaCertDetailDTO.setFiles(fileUploadDTOS);

        return qaCertDetailDTO;
    }

    @Override
    public QaCertDTO selectById(String id) {
        QaCert qaCert = qaCertMapper.selectById(id);
        QaCertDTO qaCertDTO = new QaCertDTO(qaCert);
        return qaCertDTO;
    }

    @Override
    public int del(String id) {
        return qaCertMapper.deleteById(id);
    }
}

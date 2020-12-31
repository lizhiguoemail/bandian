package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.jt.DTO.response.TitleCertDTO;
import com.lhsz.bandian.jt.entity.TitleCert;
import com.lhsz.bandian.jt.mapper.TitleCertMapper;
import com.lhsz.bandian.jt.service.ITitleCertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 职称证书 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class TitleCertServiceImpl extends ServiceImpl<TitleCertMapper, TitleCert> implements ITitleCertService {
    @Autowired
    private com.lhsz.bandian.jt.mapper.TitleCertMapper TitleCertMapper;
    @Autowired
    private TokenService tokenService;
    @Override
    public List<TitleCertDTO> list(TitleCert titleCert) {
        ArrayList<TitleCertDTO> titleCertDTOS = new ArrayList<>();
        QueryWrapper<TitleCert> titleCertQueryWrapper = new QueryWrapper<>();
        if (titleCert.getFullName()!=null&&!"".equals(titleCert.getFullName().trim())){
            titleCertQueryWrapper.like("full_name",titleCert.getFullName());
        }
        if (titleCert.getGender()!=null&&!"".equals(titleCert.getGender().trim())){
            titleCertQueryWrapper.like("gender",titleCert.getGender());
        }

        List<TitleCert> list = TitleCertMapper.selectList(titleCertQueryWrapper);
        for (TitleCert titleCert1 : list) {
            TitleCertDTO titleCertDTO = new TitleCertDTO(titleCert1);
            titleCertDTOS.add(titleCertDTO);
        }
        return titleCertDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(TitleCertDTO titleCertDTO) {
        TitleCert titleCert = new TitleCert();
        BeanUtils.copyProperties(titleCertDTO,titleCert);
        titleCert.setCertId(titleCertDTO.getId());
        updateById(titleCert);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(TitleCertDTO titleCertDTO) {
        TitleCert titleCert = new TitleCert();
        titleCertDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        titleCertDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(titleCertDTO,titleCert);
        titleCertDTO.setId(titleCert.getCertId());

        save(titleCert);
    }

    @Override
    public TitleCertDTO selectById(String id) {
        TitleCert titleCert = TitleCertMapper.selectById(id);
        TitleCertDTO titleCertDTO = new TitleCertDTO(titleCert);
        return titleCertDTO;
    }

    @Override
    public int del(String id) {
        return TitleCertMapper.deleteById(id);
    }
}

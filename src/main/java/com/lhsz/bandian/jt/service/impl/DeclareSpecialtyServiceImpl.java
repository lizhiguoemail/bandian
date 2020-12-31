package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.Exception.NoticeException;
import com.lhsz.bandian.jt.DTO.response.JtDeclareSpecialtyDTO;
import com.lhsz.bandian.jt.entity.DeclareSpecialty;
import com.lhsz.bandian.jt.mapper.DeclareSpecialtyMapper;
import com.lhsz.bandian.jt.service.IDeclareSpecialtyService;
import com.lhsz.bandian.utils.CopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 申报专业 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class DeclareSpecialtyServiceImpl extends ServiceImpl<DeclareSpecialtyMapper, DeclareSpecialty> implements IDeclareSpecialtyService {


    @Override
    public List<JtDeclareSpecialtyDTO> listDeclareSpecialty(DeclareSpecialty declareSpecialty) {
        List<JtDeclareSpecialtyDTO> result = new ArrayList<>();
        QueryWrapper<DeclareSpecialty> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_Id",declareSpecialty.getCategoryId());
        if(declareSpecialty.getEnabled()!=null) {
            queryWrapper.eq("enabled",declareSpecialty.getEnabled());
        }
        if (declareSpecialty.getCode() != null && !"".equals(declareSpecialty.getCode().trim())) {
            queryWrapper.like("code",declareSpecialty.getCode());
        }
        if (declareSpecialty.getName() != null && !"".equals(declareSpecialty.getName().trim())) {
            queryWrapper.like("name",declareSpecialty.getName());
        }
        queryWrapper.orderByAsc("code");
        List<DeclareSpecialty> DeclareSpecialtyList = baseMapper.selectList(queryWrapper);
        DeclareSpecialtyList.forEach(obj->result.add(new JtDeclareSpecialtyDTO(obj)));
        return result;
    }

    @Override
    public JtDeclareSpecialtyDTO getDeclareSpecialty(String id) {
        DeclareSpecialty declareSpecialty = baseMapper.selectById(id);
        JtDeclareSpecialtyDTO jtDeclareSpecialtyDTO = new JtDeclareSpecialtyDTO(declareSpecialty);
//        BeanUtils.copyProperties(declareSpecialty,jtDeclareSpecialtyDTO);
//        jtDeclareSpecialtyDTO.setId(declareSpecialty.getSpecialtyId());
        return jtDeclareSpecialtyDTO;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addDeclareSpecialty(JtDeclareSpecialtyDTO jtDeclareSpecialtyDTO) {
        check(jtDeclareSpecialtyDTO);
        DeclareSpecialty declareSpecialty = new DeclareSpecialty();
        CopyUtils.copyProperties(jtDeclareSpecialtyDTO,declareSpecialty);
        jtDeclareSpecialtyDTO.setId(declareSpecialty.getSpecialtyId());
        save(declareSpecialty);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateDeclareSpecialty(JtDeclareSpecialtyDTO jtDeclareSpecialtyDTO) {
        checkUpdate(jtDeclareSpecialtyDTO);
        DeclareSpecialty declareSpecialty = new DeclareSpecialty();
        BeanUtils.copyProperties(jtDeclareSpecialtyDTO,declareSpecialty);
        declareSpecialty.setSpecialtyId(jtDeclareSpecialtyDTO.getId());
        updateById(declareSpecialty);
    }


    @Override
    public int delDeclareSpecialty(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public List<SelectDTO> listAllByCategoryId(String categoryId) {
        List<SelectDTO> result = new ArrayList<>();
        QueryWrapper<DeclareSpecialty> queryWrapper = new QueryWrapper<>();
        if(categoryId!=null){
            queryWrapper.eq("category_id",categoryId);
        }
        list(queryWrapper).forEach(obj->result.add(new SelectDTO(obj.getSpecialtyId(),obj.getName(),obj.getCode(),false)));
        return result;
    }

    private void check(JtDeclareSpecialtyDTO jtDeclareSpecialtyDTO) {
        String code=jtDeclareSpecialtyDTO.getCode();
        String name = jtDeclareSpecialtyDTO.getName();
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("code",code);
        queryWrapper.or();
        queryWrapper.eq("name",name);
        queryWrapper.eq("category_Id",jtDeclareSpecialtyDTO.getCategoryId());
        if(list(queryWrapper).size()>0){
            throw new NoticeException("职称编码或职称名称已存在！");
        }

    }
    private void checkUpdate(JtDeclareSpecialtyDTO jtDeclareSpecialtyDTO) {
        String code=jtDeclareSpecialtyDTO.getCode();
        String name = jtDeclareSpecialtyDTO.getName();
        DeclareSpecialty oldObj = getById(jtDeclareSpecialtyDTO.getId());
        if(!oldObj.getCode().equals(code)||!oldObj.getName().equals(name)){
            QueryWrapper queryWrapper =new QueryWrapper();
            queryWrapper.eq("category_Id",jtDeclareSpecialtyDTO.getCategoryId());
            if(!oldObj.getCode().equals(code)){
                queryWrapper.eq("code",code);
                queryWrapper.or();
            }
            if(!oldObj.getName().equals(name)){
                queryWrapper.eq("name",name);
            }

            if(list(queryWrapper).size()>0){
                throw new NoticeException("职称编码或职称名称已存在！");
            }
        }
    }
}

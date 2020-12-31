package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.Exception.NoticeException;
import com.lhsz.bandian.jt.DTO.response.JtDeclareTitleDTO;
import com.lhsz.bandian.jt.entity.DeclareTitle;
import com.lhsz.bandian.jt.mapper.DeclareTitleMapper;
import com.lhsz.bandian.jt.service.IDeclareTitleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 申报职称 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class DeclareTitleServiceImpl extends ServiceImpl<DeclareTitleMapper, DeclareTitle> implements IDeclareTitleService {


    @Override
    public List<JtDeclareTitleDTO> listDeclareTitle(DeclareTitle declareTitle) {
        List<JtDeclareTitleDTO> result = new ArrayList<>();
        QueryWrapper<DeclareTitle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_Id",declareTitle.getCategoryId());
        if(declareTitle.getEnabled()!=null) {
            queryWrapper.eq("enabled",declareTitle.getEnabled());
        }
        if (declareTitle.getCode() != null && !"".equals(declareTitle.getCode().trim())) {
            queryWrapper.like("code",declareTitle.getCode());
        }
        if (declareTitle.getName() != null && !"".equals(declareTitle.getName().trim())) {
            queryWrapper.like("name",declareTitle.getName());
        }
        queryWrapper.orderByAsc("code");
        List<DeclareTitle> declareTitles = list(queryWrapper);
        declareTitles.forEach(obj->result.add(new JtDeclareTitleDTO(obj)));
        return result;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public JtDeclareTitleDTO getDeclareTitle(String id) {
        DeclareTitle declareTitle = getById(id);
        JtDeclareTitleDTO jtDeclareTitleDTO = new JtDeclareTitleDTO();
        BeanUtils.copyProperties(declareTitle,jtDeclareTitleDTO);
        jtDeclareTitleDTO.setId(declareTitle.getTitleId());
        return jtDeclareTitleDTO;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addDeclareTitle(JtDeclareTitleDTO jtDeclareTitleDTO) {
        check(jtDeclareTitleDTO);
        DeclareTitle declareTitle = new DeclareTitle();
        BeanUtils.copyProperties(jtDeclareTitleDTO,declareTitle);
        jtDeclareTitleDTO.setId(declareTitle.getTitleId());
        save(declareTitle);
    }

    private void check(JtDeclareTitleDTO jtDeclareTitleDTO) {
        String code=jtDeclareTitleDTO.getCode();
        String name = jtDeclareTitleDTO.getName();
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("code",code);
        queryWrapper.or();
        queryWrapper.eq("name",name);
        queryWrapper.eq("category_Id",jtDeclareTitleDTO.getCategoryId());
       if(list(queryWrapper).size()>0){
           throw new NoticeException("专业编码或专业名称已存在！");
       }

    }
    private void checkUpdate(JtDeclareTitleDTO jtDeclareTitleDTO) {
        String code=jtDeclareTitleDTO.getCode();
        String name = jtDeclareTitleDTO.getName();
        DeclareTitle oldObj = getById(jtDeclareTitleDTO.getTitleId());
        if(!oldObj.getCode().equals(code)||!oldObj.getName().equals(name)){
            QueryWrapper queryWrapper =new QueryWrapper();
            queryWrapper.eq("category_Id",jtDeclareTitleDTO.getCategoryId());
            if(!oldObj.getCode().equals(code)){
                queryWrapper.eq("code",code);
                queryWrapper.or();
            }
            if(!oldObj.getName().equals(name)){
                queryWrapper.eq("name",name);
            }

            if(list(queryWrapper).size()>0){
                throw new NoticeException("专业编码或专业名称已存在！");
            }
        }
    }
    @Override
    public void updateDeclareTitle(JtDeclareTitleDTO jtDeclareTitleDTO) {
        checkUpdate(jtDeclareTitleDTO);
        DeclareTitle declareTitle = new DeclareTitle();
        BeanUtils.copyProperties(jtDeclareTitleDTO,declareTitle);
        declareTitle.setTitleId(jtDeclareTitleDTO.getId());
        updateById(declareTitle);
    }


    @Override
    public int delDeclareTitle(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public List<SelectDTO> listAllByCategoryId(String categoryId) {
        List<SelectDTO> result = new ArrayList<>();
        QueryWrapper<DeclareTitle> queryWrapper = new QueryWrapper<>();
        if(categoryId!=null){
            queryWrapper.eq("category_id",categoryId);
        }
        list(queryWrapper).forEach(obj->result.add(new SelectDTO(obj.getTitleId(),obj.getName(),obj.getCode(),false)));
        return result;
    }

    @Override
    public DeclareTitle getFlowIdByCode(String code) {
        QueryWrapper<DeclareTitle> queryWrapper = new QueryWrapper<>();
        if(code != null){
            queryWrapper.eq("code", code);
        }
        DeclareTitle declareTitle = getOne(queryWrapper);
        return declareTitle;
    }
}

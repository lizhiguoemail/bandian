package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.jt.DTO.response.JuryDeclareSubjectDTO;
import com.lhsz.bandian.jt.DTO.response.JuryDeptDTO;
import com.lhsz.bandian.jt.entity.JuryDeclareSubject;
import com.lhsz.bandian.jt.entity.JuryDept;
import com.lhsz.bandian.jt.mapper.JuryDeclareSubjectMapper;
import com.lhsz.bandian.jt.mapper.JuryDeptMapper;
import com.lhsz.bandian.jt.service.IJuryDeclareSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评委会申报对象资格 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class JuryDeclareSubjectServiceImpl extends ServiceImpl<JuryDeclareSubjectMapper, JuryDeclareSubject> implements IJuryDeclareSubjectService {

    private final JuryDeclareSubjectMapper juryDeclareSubjectMapper;
    private final JuryDeptMapper juryDeptMapper;

    @Autowired
    public JuryDeclareSubjectServiceImpl(JuryDeclareSubjectMapper juryDeclareSubjectMapper,JuryDeptMapper juryDeptMapper) {
        this.juryDeclareSubjectMapper = juryDeclareSubjectMapper;
        this.juryDeptMapper=juryDeptMapper;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean add(JuryDeclareSubject juryDeclareSubject) {
        juryDeclareSubject.setIsDeleted(0);
        boolean saveState = save(juryDeclareSubject);
        return saveState;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(JuryDeclareSubject juryDeclareSubject) {
        updateById(juryDeclareSubject);
    }

    @Override
    public List<JuryDeclareSubjectDTO> list(JuryDeclareSubject juryDeclareSubject) {
        QueryWrapper<JuryDeclareSubject> juryDeclareSubjectQueryWrapper = new QueryWrapper<>();
        if (juryDeclareSubject.getDeptId()!=null&&!"".equals(juryDeclareSubject.getDeptId().trim())){
            juryDeclareSubjectQueryWrapper.like("dept_id",juryDeclareSubject.getDeptId());
        }

        List<JuryDeclareSubjectDTO> list = juryDeclareSubjectMapper.selectMapperList(juryDeclareSubject);
        return list;

    }

    @Override
    public JuryDeclareSubjectDTO selectById(String id) {
        JuryDeclareSubject juryDeclareSubject = juryDeclareSubjectMapper.selectById(id);
        JuryDeclareSubjectDTO juryDeclareSubjectDTO = new JuryDeclareSubjectDTO();
        BeanUtils.copyProperties(juryDeclareSubject,juryDeclareSubjectDTO);
        juryDeclareSubjectDTO.setId(juryDeclareSubject.getSubjectId());
        return juryDeclareSubjectDTO;
    }

    @Override
    public int del(String id) {
        return juryDeclareSubjectMapper.deleteById(id);
    }

    @Override
    public List<JuryDeptDTO> search(JuryDeclareSubjectDTO juryDeclareSubjectDTO) {
        List<JuryDeptDTO> search = juryDeclareSubjectMapper.search(juryDeclareSubjectDTO.getDeclareCategory(),juryDeclareSubjectDTO.getDeclareSpecialty(),juryDeclareSubjectDTO.getDeclareTitle(),juryDeclareSubjectDTO.getProvince(),juryDeclareSubjectDTO.getCity(),juryDeclareSubjectDTO.getCounty());
        return search;
    }

}

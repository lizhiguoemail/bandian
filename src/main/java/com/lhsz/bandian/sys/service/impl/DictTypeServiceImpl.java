package com.lhsz.bandian.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.sys.DTO.result.DictTypeDTO;
import com.lhsz.bandian.sys.entity.DictType;
import com.lhsz.bandian.sys.mapper.DictTypeMapper;
import com.lhsz.bandian.sys.service.IDictDataService;
import com.lhsz.bandian.sys.service.IDictTypeService;
import com.lhsz.bandian.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字典类型 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements IDictTypeService {
    @Autowired
    DictTypeMapper dictTypeMapper;

    @Autowired
    IDictDataService dictDataService;
    @Autowired
    RedisCache redisCache;
    @Override
    public List<DictTypeDTO> list(DictType dictType) {
        ArrayList<DictTypeDTO> dictTypeDTOS = new ArrayList<>();
        QueryWrapper queryWrapper=new QueryWrapper();
        if (dictType.getDictName()!=null&&!"".equals(dictType.getDictName().trim())){
            queryWrapper.like("dict_name",dictType.getDictName());
        }
        if (dictType.getType()!=null&&!"".equals(dictType.getType().trim())){
            queryWrapper.like("type",dictType.getType());
        }
        List<DictType> list = dictTypeMapper.selectList(queryWrapper);
        for (DictType type : list) {
            DictTypeDTO dictTypeDTO = new DictTypeDTO();
            BeanUtils.copyProperties(type,dictTypeDTO);
            dictTypeDTO.setId(type.getDictId());
            dictTypeDTO.setDictName(type.getDictName());
            dictTypeDTO.setType(type.getType());
            dictTypeDTO.setEnabled(type.getEnabled());
            dictTypeDTO.setCreationTime(type.getCreationTime());
            dictTypeDTOS.add(dictTypeDTO);
        }
        return dictTypeDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(DictTypeDTO dictTypeDTO) {
        DictType dictType = new DictType();
        BeanUtils.copyProperties(dictTypeDTO,dictType);
        dictTypeDTO.setId(dictType.getDictId());
        redisCache.deleteObject("dictList");
        updateById(dictType);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void save(DictTypeDTO dictTypeDTO) {
        DictType dictType = new DictType();
        BeanUtils.copyProperties(dictTypeDTO,dictType);
        dictType.setIsDeleted(0);
        redisCache.deleteObject("dictList");
        save(dictType);
    }

    @Override
    public DictTypeDTO selectById(String id) {
        DictType dictType = dictTypeMapper.selectById(id);
        DictTypeDTO dictTypeDTO = new DictTypeDTO();
        BeanUtils.copyProperties(dictType,dictTypeDTO);
        dictTypeDTO.setId(dictType.getDictId());
        return dictTypeDTO;
    }

    @Override
    public int del(String id) {

        redisCache.deleteObject("dictList");
        return dictTypeMapper.deleteById(id);
    }


}

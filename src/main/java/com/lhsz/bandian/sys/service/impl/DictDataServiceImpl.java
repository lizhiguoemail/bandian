package com.lhsz.bandian.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.sys.DTO.result.DictDataDTO;
import com.lhsz.bandian.sys.entity.DictData;
import com.lhsz.bandian.sys.entity.DictType;
import com.lhsz.bandian.sys.mapper.DictDataMapper;
import com.lhsz.bandian.sys.service.IDictDataService;
import com.lhsz.bandian.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典数据 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements IDictDataService {

    @Autowired
    DictDataMapper dictDataMapper;
    @Autowired
    DictTypeServiceImpl dictTypeService;
    @Autowired
    RedisCache redisCache;
    @Override
    public List<DictDataDTO> list(DictData dictData) {
        ArrayList<DictDataDTO> dictDataDTOS = new ArrayList<>();
        QueryWrapper queryWrapper=new QueryWrapper();
        if (dictData.getDictCode()!=null&&!"".equals(dictData.getDictCode().trim())){
            queryWrapper.like("dict_code",dictData.getDictCode());
        }
        if (dictData.getDictLabel()!=null&&!"".equals(dictData.getDictLabel().trim())){
            queryWrapper.like("dict_label",dictData.getDictLabel());
        }
        if (dictData.getDictType()!=null &&!"".equals(dictData.getDictType().trim())){
            queryWrapper.eq("dict_type",dictData.getDictType());
        }
        List<DictData> list = dictDataMapper.selectList(queryWrapper);
        for (DictData data : list) {
            DictDataDTO dictDataDTO = new DictDataDTO();
            BeanUtils.copyProperties(data,dictDataDTO);
            dictDataDTO.setId(data.getDictId());
            dictDataDTOS.add(dictDataDTO);
        }
        return dictDataDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(DictDataDTO dictDataDTO) {
        DictData dictData = new DictData();
        BeanUtils.copyProperties(dictDataDTO,dictData);
        dictData.setDictId(dictDataDTO.getId());
        updateById(dictData);
        redisCache.deleteObject("dictList");
        //更新缓存中的数据
        List<DictType> dictTypeList1 = dictTypeService.list();
        if (dictTypeList1.size()>0){
            redisCache.setCacheList("dictList",dictTypeList1);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void save(DictDataDTO dictDataDTO) {
        DictData dictData = new DictData();
        BeanUtils.copyProperties(dictDataDTO,dictData);
        dictDataDTO.setId(dictData.getDictId());
        dictData.setDictCode("n");
        dictData.setIsDeleted(0);
        redisCache.deleteObject("dictList");
        save(dictData);
    }

    @Override
    public DictDataDTO selectById(String id) {
        DictData dictData = dictDataMapper.selectById(id);
        DictDataDTO dictDataDTO = new DictDataDTO();
        BeanUtils.copyProperties(dictData,dictDataDTO);
        dictDataDTO.setId(dictData.getDictId());
        return dictDataDTO;
    }



    @Override
    public List<SelectDTO> selectByDictType(String dictType) {
        List<SelectDTO> dictDatasDTOS = new ArrayList<>();

        QueryWrapper queryWrapper=new QueryWrapper();
        if (dictType!=null &&!"".equals(dictType.trim())){
            queryWrapper.eq("dict_type",dictType);
            queryWrapper.orderBy(true, true,"dict_sort");
        }
        List<DictData> list = dictDataMapper.selectList(queryWrapper);
        for (DictData dictData : list) {
            SelectDTO dictDatasDTO = new SelectDTO();
            //dictDatasDTO.setId(dictData.getDictId());
            dictDatasDTO.setText(dictData.getDictLabel());
            dictDatasDTO.setValue(dictData.getDictValue());
            dictDatasDTOS.add(dictDatasDTO);
        }

        return dictDatasDTOS;
    }


    @Override
    public String selectByDictTypeAndValue2(String dictType, String dictValue) {
        List<Map<String, String>> list = selectByDictType2(dictType);
        if (list.size()>0){
            for (Map<String, String> map : list) {
                for(Map.Entry<String, String> entry : map.entrySet()){
                    if (entry.getKey().equals(dictValue)){
                        return entry.getValue();
                    }
                }

            }
        }
        return null;

    }
    public List<Map<String,String>> selectByDictType2(String dictType) {
        Map<String,List<Map<String,String>>> map= selectAll2();
        List<Map<String, String>> maps = map.get(dictType);
        return maps;
    }

    public Map<String,List<Map<String,String>>> selectAll2() {
        List<DictType> dictList = redisCache.getCacheList("dictList");
        if (dictList.size()>0 ){
            Map<String,List<Map<String,String>>> map1=new HashMap<>();
            List<DictData> list = list();
            for(DictType dictType : dictList) {
                List<Map<String ,String >> mapList = new ArrayList<>();
                for (DictData dictData : list) {
                    if(dictData.getDictType().equals(dictType.getType())){
                        Map<String, String> map2 = new HashMap<>();
                        map2.put(dictData.getDictValue(),dictData.getDictLabel());
                        mapList.add(map2);
                    }
                }
                map1.put(dictType.getType(),mapList);
            }
            return map1;
        }else {
            Map<String,List<Map<String,String>>> map1=new HashMap<>();

            List<DictData> list = list();
            List<DictType> dictTypeList = dictTypeService.list();
            if (dictTypeList.size()>0){
                redisCache.setCacheList("dictList",dictTypeList);
            }
            for (DictType dictType : dictTypeList) {
                List<Map<String ,String >> mapList = new ArrayList<>();
                for (DictData dictData : list) {
                    if(dictData.getDictType().equals(dictType.getType())){
                        Map<String, String> map2 = new HashMap<>();
                        map2.put(dictData.getDictValue(),dictData.getDictLabel());
                        mapList.add(map2);
                    }
                }
                map1.put(dictType.getType(),mapList);
            }
            return map1;
        }

    }
    @Override
    public int del(String id) {
        redisCache.deleteObject("dictList");
        return dictDataMapper.deleteById(id);

    }
}

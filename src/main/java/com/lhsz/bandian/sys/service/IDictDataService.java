package com.lhsz.bandian.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.sys.DTO.result.DictDataDTO;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.sys.entity.DictData;

import java.util.List;

/**
 * <p>
 * 字典数据 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
public interface IDictDataService extends IService<DictData> {
    List<DictDataDTO> list(DictData dictData);
    void update(DictDataDTO dictDataDTO);
    void save(DictDataDTO dictDataDTO);
    DictDataDTO selectById(String id);

    List<SelectDTO> selectByDictType(String dictType);
    String  selectByDictTypeAndValue2(String dictType,String dictValue);
    int del(String id);

}

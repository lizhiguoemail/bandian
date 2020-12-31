package com.lhsz.bandian.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.sys.DTO.result.DictTypeDTO;
import com.lhsz.bandian.sys.entity.DictType;

import java.util.List;

/**
 * <p>
 * 字典类型 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
public interface IDictTypeService extends IService<DictType> {

    List<DictTypeDTO> list(DictType dictType);
    void update(DictTypeDTO dictTypeDTO);
    void save(DictTypeDTO dictTypeDTO);
    DictTypeDTO selectById(String id);

    int del(String id);
}

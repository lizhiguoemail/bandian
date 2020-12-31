package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.WorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.WorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.WorkUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.WorkDTO;
import com.lhsz.bandian.jt.entity.Work;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 工作经历 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IWorkService extends IService<Work> {
    List<WorkDTO> list(Work work);
    void update(WorkDTO workDTO);
    void update(WorkUpdateDTO workDTO);
    void add(WorkDTO workDTO);
    void add(WorkAddDTO workDTO);
    WorkDTO selectById(String id);
    WorkDetailDTO detailById(String id);
    int del(String id);
}

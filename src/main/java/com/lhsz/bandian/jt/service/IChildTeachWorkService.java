package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.ChildTeachOpenDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachWorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachWorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachWorkUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtChildTeachWorkDTO;
import com.lhsz.bandian.jt.entity.ChildTeachWork;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 中小学幼儿老师日常教学 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IChildTeachWorkService extends IService<ChildTeachWork> {

    /**
     * 查询中小学幼儿老师日常教学
     * @param childTeachWork
     * @return
     */
    List<JtChildTeachWorkDTO> listChildTeachWork (ChildTeachWork childTeachWork);

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    JtChildTeachWorkDTO getChildTeachWork (String id);
    ChildTeachWorkDetailDTO detailById (String id);


    /**
     * 添加中小学幼儿老师日常教学
     * @param jtChildTeachWorkDTO
     * @return
     */
    void addChildTeachWork (JtChildTeachWorkDTO jtChildTeachWorkDTO);
    void addChildTeachWork (ChildTeachWorkAddDTO jtChildTeachWorkDTO);

    /**
     * 更新中小学幼儿老师日常教学
     * @param jtChildTeachWorkDTO
     * @return
     */
    void updateChildTeachWork (JtChildTeachWorkDTO jtChildTeachWorkDTO);
    void updateChildTeachWork (ChildTeachWorkUpdateDTO jtChildTeachWorkDTO);

    /**
     * 删除中小学幼儿老师日常教学
     * @param id
     * @return
     */
    int delChildTeachWork (String id);
}

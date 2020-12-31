package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.ChildTeachOpenAddDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachOpenDetailDTO;
import com.lhsz.bandian.jt.DTO.request.ChildTeachOpenUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtChildTeachOpenDTO;
import com.lhsz.bandian.jt.entity.ChildTeachOpen;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 中小学幼儿老师公开课 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IChildTeachOpenService extends IService<ChildTeachOpen> {

    /**
     * 查询中小学幼儿老师公开课
     * @param childTeachOpen
     * @return
     */
    List<JtChildTeachOpenDTO> listChildTeachOpen (ChildTeachOpen childTeachOpen);

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    JtChildTeachOpenDTO getChildTeachOpen (String id);
    ChildTeachOpenDetailDTO detailById (String id);

    /**
     * 添加中小学幼儿老师公开课
     * @param jtChildTeachOpenDTO
     * @return
     */
    void addChildTeachOpen (JtChildTeachOpenDTO jtChildTeachOpenDTO);
    void addChildTeachOpen (ChildTeachOpenAddDTO jtChildTeachOpenDTO);

    /**
     * 更新中小学幼儿老师公开课
     * @param jtChildTeachOpenDTO
     * @return
     */
    void updateChildTeachOpen (JtChildTeachOpenDTO jtChildTeachOpenDTO);
    void updateChildTeachOpen (ChildTeachOpenUpdateDTO jtChildTeachOpenDTO);

    /**
     * 删除中小学幼儿老师公开课
     * @param id
     * @return
     */
    int delChildTeachOpen (String id);

}

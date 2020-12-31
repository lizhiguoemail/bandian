package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.EduAddDTO;
import com.lhsz.bandian.jt.DTO.request.EduDetailDTO;
import com.lhsz.bandian.jt.DTO.request.EduUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtEduDTO;
import com.lhsz.bandian.jt.entity.Edu;
import com.lhsz.bandian.jt.entity.Edu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 教育经历 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IEduService extends IService<Edu> {

    /**
     * 查询教育经历
     * @param edu
     * @return
     */
    List<JtEduDTO> listEdu (Edu edu);

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    JtEduDTO getEdu (String id);
    EduDetailDTO detailById (String id);

    /**
     * 添加教育经历
     * @param jtEduDTO
     * @return
     */
    void addEdu (JtEduDTO jtEduDTO);
    void addEdu (EduAddDTO jtEduDTO);

    /**
     * 更新教育经历
     * @param jtEduDTO
     * @return
     */
    void updateEdu (JtEduDTO jtEduDTO);
    void updateEdu (EduUpdateDTO jtEduDTO);

    /**
     * 删除教育经历
     * @param id
     * @return
     */
    int delEdu (String id);
}

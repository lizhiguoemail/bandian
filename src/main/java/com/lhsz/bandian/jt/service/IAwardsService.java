package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.AwardsAddDTO;
import com.lhsz.bandian.jt.DTO.request.AwardsDetailDTO;
import com.lhsz.bandian.jt.DTO.request.AwardsUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtAwardsDTO;
import com.lhsz.bandian.jt.entity.Awards;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 获奖情况 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IAwardsService extends IService<Awards> {
    /**
     * 查询获奖情况
     * @param awards
     * @return
     */
    List<JtAwardsDTO> listAwards (Awards awards);

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    JtAwardsDTO getAwards (String id);
    AwardsDetailDTO detailById (String id);


    /**
     * 添加
     * @param jtAwardsDTO
     * @return
     */
    void addAwards (JtAwardsDTO jtAwardsDTO);
    void addAwards (AwardsAddDTO jtAwardsDTO);

    /**
     * 更新
     * @param jtAwardsDTO
     * @return
     */
    void updateAwards (JtAwardsDTO jtAwardsDTO);
    void updateAwards (AwardsUpdateDTO jtAwardsDTO);

    /**
     * 删除
     * @param id
     * @return
     */
    int delAwards (String id);
}

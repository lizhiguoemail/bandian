package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.jt.DTO.response.JtAwardsTypeDTO;
import com.lhsz.bandian.jt.entity.AwardsType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;

import java.util.List;

/**
 * <p>
 * 资历级别 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IAwardsTypeService extends IService<AwardsType> {

    /**
     * 查询资历级别
     * @param awardsType
     * @return
     */
    List<JtAwardsTypeDTO> listAwardsType (AwardsType awardsType);

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    JtAwardsTypeDTO getAwardsType (String id);

    /**
     * 添加资历级别
     * @param jtAwardsTypeDTO
     * @return
     */
    void addAwardsType (JtAwardsTypeDTO jtAwardsTypeDTO);

    /**
     * 更新
     * @param jtAwardsTypeDTO
     * @return
     */
    void updateAwardsType (JtAwardsTypeDTO jtAwardsTypeDTO);

    /**
     * 删除资历级别
     * @param id
     * @return
     */
    int delAwardsType (String id);

    TreeDTO getTreeDTO();
    public List<SelectDTO> listAllByParentId(String parentId);

    /**
     * 根据code进行查询
     * @param code
     * @return
     */
    JtAwardsTypeDTO getAwardsTypeNameByCode (String code);

}

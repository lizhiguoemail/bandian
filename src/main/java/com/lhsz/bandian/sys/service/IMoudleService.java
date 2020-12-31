package com.lhsz.bandian.sys.service;

import com.lhsz.bandian.sys.DTO.commit.AddResourceDTO;
import com.lhsz.bandian.sys.DTO.query.QueryMoudleDTO;
import com.lhsz.bandian.sys.DTO.result.MoudelDTO;
import com.lhsz.bandian.sys.DTO.result.MoudelParentDTO;

import java.util.List;

/**
 * @author lizhiguo
 * 2020/7/20 11:47
 */
public interface IMoudleService {

    List<MoudelDTO> listByAppId(String applicationId,String roleId);
    List<MoudelDTO> listByAppId(String applicationId);
    List<MoudelDTO> listByAppId(QueryMoudleDTO queryMoudleDTO);

    /**
     * 获取菜单集合
     * 返回结果中不包含操作按钮
     * @param applicationId
     * @return
     */
    List<MoudelParentDTO> listMouleForAdd(String applicationId);

    MoudelDTO findByRid(String id);

    boolean addMoudle(AddResourceDTO resourceDTO);

    boolean updateMoudle(MoudelDTO moudelDTO);

    boolean removeByIds(List<String> asList);
}

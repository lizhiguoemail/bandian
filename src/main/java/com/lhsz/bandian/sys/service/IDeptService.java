package com.lhsz.bandian.sys.service;

import com.lhsz.bandian.sys.DTO.query.QueryDeptDTO;
import com.lhsz.bandian.sys.DTO.result.DeptDTO;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;
import com.lhsz.bandian.sys.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
public interface IDeptService extends IService<Dept> {

    TreeDTO getTreeDTO();

    List<DeptDTO> listDTO(QueryDeptDTO queryDeptDTO);
}

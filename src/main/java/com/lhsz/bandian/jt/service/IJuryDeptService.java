package com.lhsz.bandian.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.jt.DTO.response.JuryDeptDTO;
import com.lhsz.bandian.jt.DTO.response.JuryUserDTO;
import com.lhsz.bandian.jt.entity.JuryDept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.sys.DTO.query.QueryUserDTO;

import java.util.List;

/**
 * <p>
 * 评委会主管部门 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IJuryDeptService extends IService<JuryDept> {
    boolean add(JuryDept juryDept);
    boolean add(JuryDeptDTO juryDept);

    void update(JuryDept juryDept);
    void update(JuryDeptDTO juryDept);

    List<JuryDeptDTO> list(JuryDeptDTO juryDept);

    JuryDeptDTO selectById(String id);

    int del(String id);

    List<SelectDTO> listAllBydeptType(Integer deptType);
    List<JuryDept> listBydeptType(Integer deptType);

    JuryDept getByUserId(String deptId, List<JuryDept> cacheList);

    List<JuryUserDTO> listDTO(QueryWrapper<QueryUserDTO> queryWrapper);

    List<JuryDeptDTO> listDisableDTO(JuryDept juryDept);

    JuryDept selectByUserId(String id);

    List<JuryDept> search(JuryDept juryDept);

    /**
     * 审核评委会接口
     * @param deptId 部门id
     * @param checkState true：通过 false: 拒绝
     * @return 执行结果(true or false)
     */
    boolean checkJury(String deptId, boolean checkState);
}

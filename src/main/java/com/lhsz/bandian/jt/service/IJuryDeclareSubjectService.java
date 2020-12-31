package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.response.JuryDeclareSubjectDTO;
import com.lhsz.bandian.jt.DTO.response.JuryDeptDTO;
import com.lhsz.bandian.jt.entity.JuryDeclareSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.jt.entity.JuryDept;

import java.util.List;

/**
 * <p>
 * 评委会申报对象资格 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IJuryDeclareSubjectService extends IService<JuryDeclareSubject> {

    boolean add(JuryDeclareSubject juryDeclareSubject);

    void update(JuryDeclareSubject juryDeclareSubject);

    List<JuryDeclareSubjectDTO> list(JuryDeclareSubject juryDeclareSubject);

    JuryDeclareSubjectDTO selectById(String id);

    int del(String id);

    List<JuryDeptDTO> search(JuryDeclareSubjectDTO juryDeclareSubject);

}

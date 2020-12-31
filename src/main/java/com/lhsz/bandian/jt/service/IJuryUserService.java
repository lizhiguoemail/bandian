package com.lhsz.bandian.jt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.jt.DTO.response.JuryUserDTO;
import com.lhsz.bandian.sys.DTO.AppData;
import com.lhsz.bandian.sys.DTO.query.QueryUserDTO;
import com.lhsz.bandian.sys.DTO.result.UserDTO;
import com.lhsz.bandian.sys.entity.User;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
public interface IJuryUserService extends IService<User> {

    void addUserAndJury(JuryUserDTO userDTO);

    void updateUserAndJury(JuryUserDTO userDTO);

    List<JuryUserDTO> listQueryUserDTO(QueryUserDTO user);

    JuryUserDTO getUserDTO(String id);

    void delUserAndJury(String id);
}

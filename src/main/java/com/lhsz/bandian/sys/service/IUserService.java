package com.lhsz.bandian.sys.service;

import com.lhsz.bandian.sys.DTO.AppData;
import com.lhsz.bandian.sys.DTO.query.QueryUserDTO;
import com.lhsz.bandian.sys.DTO.result.UserDTO;
import com.lhsz.bandian.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface IUserService extends IService<User> {
    /**
     * 根据用户名查找用户
     * @param userName
     * @return
     */
    User findByUsername(String userName);

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    Set<String> findPermissions(String userName);
    public int del(String id);

    AppData getApp_data();

    List<UserDTO> listQueryUserDTO(QueryUserDTO user);

    UserDTO getUserDTO(String id);
}

package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.entity.JuryDeptUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评委会主管部门用户 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IJuryDeptUserService extends IService<JuryDeptUser> {
    boolean add(JuryDeptUser juryDeptUser);

    void update(JuryDeptUser juryDeptUser);

    List<JuryDeptUser> list(JuryDeptUser juryDeptUser);

    List<JuryDeptUser> selectById(String id);

    int del(String id);

    List<JuryDeptUser> listAll();

    /**
     * 工具方法，在集合中 根据ID获取对象，适用于循环，直接传入缓存数据集，减少数据库，和缓存的读取
     * @param userId
     * @param cacheList
     * @return
     */
    JuryDeptUser getJuryDeptUser(String userId, List<JuryDeptUser> cacheList);

    JuryDeptUser selectByUserId(String id);

    void trueToDelete(String userId);
}

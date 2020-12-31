package com.lhsz.bandian.jt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhsz.bandian.jt.entity.TitleCheck;

/**
 * <p>
 * 申报职称审核 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-08-13
 */
public interface ITitleCheckService extends IService<TitleCheck> {
    void add(TitleCheck titleCheck);

    void updateByApplyId(TitleCheck titleCheck);

    int countByApplyId(String applyId);

    boolean delByApplyId(String applyId, int deptType);

}

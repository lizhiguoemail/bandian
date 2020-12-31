package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.entity.Flow;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 申报流程 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Repository
public interface FlowMapper extends MyBaseMapper<Flow> {
    String selectFlowValueByUserId(String userId);
}

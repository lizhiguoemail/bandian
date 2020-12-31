package com.lhsz.bandian.jt.mapper;

import com.lhsz.bandian.jt.DTO.request.*;
import com.lhsz.bandian.jt.DTO.response.TitleApplyDTO;
import com.lhsz.bandian.jt.entity.TitleApply;
import com.lhsz.bandian.jt.entity.TitleApplyChart;
import com.lhsz.bandian.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 申报职称 Mapper 接口
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface TitleApplyMapper extends MyBaseMapper<TitleApply> {
    void updateStatus(ChkDTO chkDTO);

    int updateCheck(TitleApplyCheckDTO titleApplyCheckDTO);

    List<TitleApplyDTO> selectMapperList(TitleApply titleApply);

    TitleApplyCheckCountDTO selectJtCount(@Param("id") String userId);

    TitleApplyCheckCountDTO selectRequiredCount(@Param("id") String userId);

    TitleApplyCountDTO selectStateCount(TitleApply titleApply);

    /**
     * 使用 userId 获得业绩库各个项目的各个状态统计
     * @param userId 用户id
     * @return 业绩库各个项目的各个状态统计
     */
    TitleApplyJtStateCountDTO selectJtStateCount(@Param("id") String userId);

    /**
     * 主申请记录提交次数加一
     * @param userId 用户id
     * @return 影响行数
     */
    int updateSubmitTimes(@Param("userId") String userId);

    /**
     * 最近一周新增申报人数(按日期分组)
     */
    List<TitleApplyChart> selectNewApplyCount();

    /**
     * 最近一周待审核申报人数(按日期分组)
     */
    List<TitleApplyChart> selectWaitAuditCount();
    /**
     * 最近一周审核通过人数(按日期分组)
     */
    List<TitleApplyChart> selectPassCount();
    /**
     * 最近一周审核不通过人数(按日期分组)
     */
    List<TitleApplyChart> selectNoPassCount();

    /**
     * 图表统计数据
     */
    List<TitleApplyChart> selectApplyChart(@Param("chkOfficeId") String chkOfficeId);
}

package com.lhsz.bandian.jt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itextpdf.text.DocumentException;
import com.lhsz.bandian.jt.DTO.request.TitleApplyCheckCountDTO;
import com.lhsz.bandian.jt.DTO.request.TitleApplyCheckDTO;
import com.lhsz.bandian.jt.DTO.request.TitleApplyCountDTO;
import com.lhsz.bandian.jt.DTO.request.TitleApplyJtStateCountDTO;
import com.lhsz.bandian.jt.DTO.response.TitleApplyDTO;
import com.lhsz.bandian.jt.entity.ApplyCount;
import com.lhsz.bandian.jt.entity.TitleApply;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 申报职称 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface ITitleApplyService extends IService<TitleApply> {
    List<TitleApplyDTO> list(TitleApply TitleApply);
    void update(TitleApplyDTO TitleApplyDTO);
    void add(TitleApplyDTO TitleApplyDTO) throws Exception;
    TitleApplyDTO selectById(String id);
    TitleApplyDTO selectByUserId(String id);
    int del(String id);
    void addCheck(TitleApplyCheckDTO titleApplyCheckDTO) throws Exception;
    void addResetCheck(TitleApplyCheckDTO titleApplyCheckDTO) throws Exception;
    String getPDF(String userId) throws IOException, DocumentException;
    TitleApplyCheckCountDTO getCheckCount(String userId);
    TitleApplyCountDTO getStateCount(TitleApply titleApply);
    HashMap<String,Integer> getJtTotalStateCount(String userId);
    TitleApplyJtStateCountDTO getJtStateCount(String userId);
    List<ApplyCount> getTotalStateChart();
    String getCertificate(String userId) throws IOException, DocumentException;
}

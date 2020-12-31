package com.lhsz.bandian.jt.DTO.request;

import lombok.Data;

/**
 * @author zhusenlin
 * Date on 2020/8/12  15:26
 */
@Data
public class TitleApplyCountDTO {
    /**
     * 待主管部门审核数
     */
    public Integer waitAdminDeptCount;
    /**
     * 主管部门审核通过数
     */
    public Integer passAdminDeptCount;
    /**
     * 主管部门审核不通过数
     */
    public Integer rejectAdminDeptCount;
    /**
     * 待评委会审核数
     */
    public Integer waitJuryCount;
    /**
     * 评委会审核通过数
     */
    public Integer passJuryCount;
    /**
     * 评委会审核不通过数
     */
    public Integer rejectJuryCount;
}

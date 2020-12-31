package com.lhsz.bandian.jt.utils;

/**
 * @author zhusenlin
 * Date on 2020/8/17  14:25
 */
public class JtApplyConstants {
    /**
     * 流程值：1.用户 ---- 主管部门
     */
    public static final String USER_TO_ADMIN = "1";
    /**
     * 流程值：2.用户 ---- 评委会
     */
    public static final String USER_TO_JURY = "2";
    /**
     * 流程值：3.用户 ---- 主管部门 ---- 评委会
     */
    public static final String USER_TO_ADMIN_THEN_JURY = "3";
    /**
     * 部门类型：主管部门
     */
    public static final Integer ADMIN_DEPT = 1;
    /**
     * 部门类型：评委会
     */
    public static final Integer JURY_DEPT = 2;
    /**
     * 主审核状态：待主管部门审核
     */
    public static final String WAIT_ADMIN_DEPT = "01";
    /**
     * 主审核状态：主管部门审核通过
     */
    public static final String PASS_ADMIN_DEPT = "02";
    /**
     * 主审核状态：主管部门审核不通过
     */
    public static final String REJECT_ADMIN_DEPT = "03";
    /**
     * 主审核状态：待评委会审核
     */
    public static final String WAIT_JURY = "04";
    /**
     * 主审核状态：评委会审核通过
     */
    public static final String PASS_JURY = "05";
    /**
     * 主审核状态：评委会审核不通过
     */
    public static final String REJECT_JURY = "06";
    /**
     * 业绩库项目审核状态：待审核
     */
    public static final String WAIT_AUDIT = "01";
    /**
     * 业绩库项目审核状态：退回
     */
    public static final String REJECT = "02";
    /**
     * 业绩库项目审核状态：不通过
     */
    public static final String NO_PASS = "03";
    /**
     * 业绩库项目审核状态：通过
     */
    public static final String PASS = "04";
}

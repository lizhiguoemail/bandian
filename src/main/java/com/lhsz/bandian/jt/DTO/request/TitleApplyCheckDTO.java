package com.lhsz.bandian.jt.DTO.request;

import lombok.Data;

import java.util.Date;

/**
 * 职称申请审核对象
 *
 * @author zhusenlin
 * Date on 2020/8/6  8:44
 */
@Data
public class TitleApplyCheckDTO {
    /**
     * 审核人员id
     * */
    private String chkUserId;

    /**
     * 审核人员名称
     * */
    private String chkUserName;

    /**
     * 被审核人员id
     * */
    private String userId;

    /**
     * 审批单位标识
     * */
    private String chkOfficeId;

    /**
     * 审批单位名称
     * */
    private String chkOfficeName;

    /**
     * 审核时间
     * */
    private Date chkTime;

    /**
     * 主审核状态
     * */
    private String mainState;

    /**
     * 重置标记
     * 0 不重置
     * 1 重置
     * */
    private String resetFlag;

    /**
     * 申请标识
     */
    private String applyId;

    private String eduCheckState;
    private String trainingEduCheckState;
    private String workCheckState;
    private String awardsCheckState;
    private String honorsCheckState;
    private String incentiveCheckState;
    private String qaCertCheckState;
    private String thesisCheckState;
    private String acadTechCheckState;
    private String engnTechProjCheckState;
    private String makeStandardCheckState;
    private String researchProjCheckState;
    private String resultUsedCheckState;
    private String patentCheckState;
    private String writingsCheckState;
    private String childTeachOpenCheckState;
    private String childTeachWorkCheckState;
    private String partyTeachTeamWorkCheckState;
    private String partyTeachWorkCheckState;
    private String sportCoachesCheckState;
    private String sportGameCheckState;
    private String assessmentCheckState;
    private String userProfileCheckState;

    private String eduCheckReason;
    private String trainingEduCheckReason;
    private String workCheckReason;
    private String awardsCheckReason;
    private String honorsCheckReason;
    private String incentiveCheckReason;
    private String qaCertCheckReason;
    private String thesisCheckReason;
    private String acadTechCheckReason;
    private String engnTechProjReason;
    private String makeStandardReason;
    private String researchProjReason;
    private String resultUsedReason;
    private String patentCheckReason;
    private String writingsCheckReason;
    private String childTeachOpenReason;
    private String childTeachWorkReason;
    private String partyTeachTeamWorkReason;
    private String partyTeachWorkCheckReason;
    private String sportGameCheckReason;
    private String assessmentCheckReason;
    private String sportCoachesCheckReason;
    private String userProfileReason;
    private String allCheckReason;

}

package com.lhsz.bandian.jt.DTO.request;

import lombok.Data;

@Data
public class ChkDTO {
    private String userId;
    private String chkUserId;
    private String chkUserName;
    private String chkStatus;
    private String chkReason;
    private String chkTime;
}

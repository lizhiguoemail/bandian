package com.lhsz.bandian.sys.DTO.query;

import lombok.Data;

/**
 * @author lizhiguo
 * 2020/7/14 15:28
 */
@Data
public class QueryUserDTO {
    private String deptId;
    private String userName;
    private String phoneNumber;
    private String email;
    private Integer userType;

}

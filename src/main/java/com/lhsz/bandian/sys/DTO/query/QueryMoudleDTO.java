package com.lhsz.bandian.sys.DTO.query;

import lombok.Data;

/**
 * @author lizhiguo
 * 2020/7/21 17:13
 */
@Data
public class QueryMoudleDTO {
    private String applicationId;
    private String name;
    private String uri;
    private boolean enabled=true;
}

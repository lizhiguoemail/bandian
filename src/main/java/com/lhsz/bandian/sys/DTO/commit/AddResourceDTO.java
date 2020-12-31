package com.lhsz.bandian.sys.DTO.commit;

import lombok.Data;

/**
 * @author lizhiguo
 * 2020/7/21 14:51
 */
@Data
public class AddResourceDTO {
    private String applicationId;
    private boolean enabled;
    private String icon;
    private String name;
    private String parentId;
    private Integer sortId;
    private Integer type;
    private String url;
    private String method;
    private String remark;
}

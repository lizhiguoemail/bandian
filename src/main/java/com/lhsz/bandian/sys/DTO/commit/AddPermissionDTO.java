package com.lhsz.bandian.sys.DTO.commit;

import java.io.Serializable;

/**
 * @author lizhiguo
 * 2020/7/21 7:43
 */
public class AddPermissionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roleId;
    private String resourceIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}

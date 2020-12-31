package com.lhsz.bandian.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel(value = "登录",description = "登录参数")
public class LoginBean {
    @ApiModelProperty(name = "用户名",value = "username",example = "admin",required = true)
    private String username;
    @ApiModelProperty(name = "密码",value = "username",example = "admin",required = true)
    private String password;
    @ApiModelProperty(name = "客户端标识",value = "clientId",example = "dcmis-admin",required = true)
    private String clientId;
    private String grantType;//备用字段
    private String scope;//备用字段

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

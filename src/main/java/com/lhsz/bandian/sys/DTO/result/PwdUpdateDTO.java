package com.lhsz.bandian.sys.DTO.result;

import java.io.Serializable;

/**
 * @author lizhiguo
 * 2020/7/17 15:31
 */
public class PwdUpdateDTO implements Serializable {
    private String confirmPassword;
    private String newPassword;
    private String oldPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}

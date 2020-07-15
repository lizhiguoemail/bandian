package com.lhsz.bandian.sys.DTO.query;

/**
 * @author lizhiguo
 * 2020/7/14 15:28
 */
public class QueryUserDTO {
    private String userName;
    private String phoneNumber;
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

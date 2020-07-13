package com.lhsz.bandian.sys.DTO;

import com.lhsz.bandian.sys.entity.User;

import java.io.Serializable;

/**
 * @author lizhiguo
 * 2020/7/10 17:48
 */
public class UserDTO implements Serializable {
    private static final long serialVersionUID=1L;
    public String userId;
    public String name;
    public String avatar;
    public String nickName;
    public String email;
    public String relatedId;
    public String relatedName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId;
    }

    public String getRelatedName() {
        return relatedName;
    }

    public void setRelatedName(String relatedName) {
        this.relatedName = relatedName;
    }
}

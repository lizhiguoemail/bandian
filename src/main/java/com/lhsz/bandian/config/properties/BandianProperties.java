package com.lhsz.bandian.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lizhiguo
 * @Date 2020/12/18 17:36
 */
@Component
@ConfigurationProperties(prefix = "bandian")
public class BandianProperties {
    /**
     * 文件上传的根路径
     */
    public static String uploadPath;
    /**
     * true 同一个账号只能登陆一次，false 同一个账号可以多处同时登陆
     */
    public static boolean isSingleLogin;
    public static String prefixPath;
    /**
     * 审核通过的PDF文件夹的名称
     */
    public static String appraisePath;
    public static Integer limitSubmit;
    /**
     * excle保存路径
     */
    public static String excleUploadPath;
    public static String name;
    public static String version;
    public static String email;
    public static String url;

    public void setEmail(String email) {
        BandianProperties.email = email;
    }

    public void setUrl(String url) {
        BandianProperties.url = url;
    }

    public boolean isIsSingleLogin() {
        return isSingleLogin;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        BandianProperties.name = name;
    }

    public static String getVersion() {
        return version;
    }

    public  void setVersion(String version) {
        BandianProperties.version = version;
    }

    public static String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {BandianProperties.uploadPath = uploadPath;
    }

    public static boolean getIsSingleLogin() {
        return isSingleLogin;
    }

    public void setIsSingleLogin(boolean isSingleLogin) {
        BandianProperties.isSingleLogin = isSingleLogin;
    }

    public static String getPrefixPath() {
        return prefixPath;
    }

    public void setPrefixPath(String prefixPath) {
        BandianProperties.prefixPath = prefixPath;
    }

    public static String getAppraisePath() {
        return appraisePath;
    }

    public void setAppraisePath(String appraisePath) {
        BandianProperties.appraisePath = appraisePath;
    }

    public static Integer getLimitSubmit() {
        return limitSubmit;
    }

    public void setLimitSubmit(Integer limitSubmit) {
        BandianProperties.limitSubmit = limitSubmit;
    }

    public static String getExcleUploadPath() {
        return excleUploadPath;
    }

    public void setExcleUploadPath(String excleUploadPath) {
        BandianProperties.excleUploadPath = excleUploadPath;
    }
}

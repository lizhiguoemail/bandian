package com.lhsz.bandian.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author lizhiguo
 * 2020/7/8 14:23
 */
public class SecurityUtils {
    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }

    public static void main(String[] args) {
        String pwd="bandiian@1233333";
        String encryptPwd=SecurityUtils.encryptPassword(pwd);
        System.out.println(encryptPwd);
        System.out.println(SecurityUtils.matchesPassword(pwd,encryptPwd));
    }
}

package com.lhsz.bandian.utils;

/**
 * @author lizhiguo
 * 2020/7/10 16:45
 */
public class PasswordTest {
    public static void main(String[] args) {
        String pwd="admin";
        System.out.println(SecurityUtils.encryptPassword(pwd));
    }
}

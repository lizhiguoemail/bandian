package com.lhsz.bandian.utils;

import org.springframework.http.HttpStatus;

/**
 * @author lizhiguo
 * 2020/7/10 16:45
 */
public class PasswordTest {
    public static void main(String[] args) {
     /*   String pwd="admin";
        System.out.println(SecurityUtils.encryptPassword(pwd));*/
        System.out.println(HttpStatus.NOT_FOUND.hashCode());
        System.out.println(HttpStatus.NOT_FOUND.value());
        System.out.println(HttpStatus.NOT_FOUND.name());
    }
}

package com.lhsz.bandian.utils;

import java.util.UUID;

/**
 * @author lizhiguo
 * @Date 2020/8/14 10:43
 */
public class UtileTools {
    /**
     *
     * @return UUID 去掉中间的-
     */
    public static String randomUUID(){
        String s = UUID.randomUUID().toString();
        return s.replace("-","");
    }
}

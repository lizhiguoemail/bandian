package com.lhsz.bandian.utils.db;

import com.baomidou.mybatisplus.core.toolkit.AES;

/**
 * @author lizhiguo
 * @Date 2020/8/14 17:49
 */
public class MysqlPwdUtils {
    public static void main(String[] args) {
        String randomKey = AES.generateRandomKey();
        String username="root";
        String pwd="root";
//        String username="root";
//        String pwd="123456.coM";
        System.out.println("请记录密钥："+randomKey);
        System.out.println("Jar 启动时需要追加参数：\n --mpw.key="+randomKey);
        String result_username = AES.encrypt(username, randomKey);
        randomKey = AES.generateRandomKey();
        String result_pwd = AES.encrypt(pwd, randomKey);
        System.out.println("result_username:\n"+result_username);
        System.out.println("result_pwd:\n"+result_pwd);
    }
}

package com.touceng.common.utils;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: MD5加密
 * @createTime 2018年7月5日 下午4:25:22
 * @copyright: 上海投嶒网络技术有限公司
 */
public class MD5Utils {

    /**
     * @param input 原文
     * @return md5后的密文
     * @methodDesc: 功能描述: 对字符串进行Md5加密
     * @author Wu, Hua-Zheng
     * @createTime 2018年7月5日 下午4:25:43
     * @version v1.0.0
     */
    public static String md5(String input) {
        byte[] code = null;
        try {
            code = MessageDigest.getInstance("md5").digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            code = input.getBytes();
        }
        BigInteger bi = new BigInteger(code);
        return bi.abs().toString(32).toUpperCase();
    }

    /**
     * @param input 原文
     * @param salt  随机数
     * @methodDesc: 功能描述: 对字符串进行Md5加密
     * @author Wu, Hua-Zheng
     * @createTime 2018年7月5日 下午4:26:08
     * @version v1.0.0
     */
    public static String generatePasswordMD5(String input, String salt) {
        if (StringUtils.isEmpty(salt)) {
            salt = "";
        }
        return md5(salt + md5(input));
    }

    /**
     * @param input
     * @return
     * @methodDesc: 功能描述: (加盐md5算法，salt为input的一截字符串)
     * @author Wu, Hua-Zheng
     * @createTime 2018年7月5日 下午4:27:11
     * @version v1.0.0
     */
    public static String saltMD5(String input) {

        return md5(input.substring(2, 6) + md5(input));
    }


    public static void main(String[] args) {

        System.out.println(MD5Utils.md5("123456"));
    }
}

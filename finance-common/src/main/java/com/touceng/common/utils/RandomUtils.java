package com.touceng.common.utils;

import java.util.Random;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 随机工具类
 * @createTime 2018/8/2 下午3:45
 * @copyright: 上海投嶒网络技术有限公司
 */
public class RandomUtils {

    /**
     * @methodDesc: 功能描述: 生成随机字符串
     * @author Wu, Hua-Zheng
     * @createTime 2018/8/2 下午3:55
     * @version v1.0.0
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int len = str.length();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(len);

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * @methodDesc: 功能描述: 获取0到指定值中间的随机值
     * @author Wu, Hua-Zheng
     * @createTime 2018/8/2 下午3:55
     * @version v1.0.0
     */
    public static int getInteger(int max) {
        Random random = new Random();
        int result = random.nextInt(max);
        return result;
    }

    /**
     * @methodDesc: 功能描述: 获取两个整数之间的随机值
     * @author Wu, Hua-Zheng
     * @createTime 2018/8/2 下午3:54
     * @version v1.0.0
     */
    public static int getRandomNumInTwoIntNum(int x, int y) {
        Random random = new Random();
        int cha = Math.abs(x - y);
        if (cha <= 1) {
            return 0;
        } else {
            if (x > y) {
                return random.nextInt(cha) + y;
            }
            if (x < y) {
                return random.nextInt(cha) + x;
            }
        }
        return 0;
    }


    /**
     * @methodDesc: 功能描述: 根据长度随机生成纯数字字符串
     * @author Wu, Hua-Zheng
     * @createTime 2018/8/2 下午3:54
     * @version v1.0.0
     */
    public static String getRandomNumber(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int len = str.length();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(len);

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}

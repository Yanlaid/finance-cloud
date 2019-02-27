package com.touceng.common.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 字符串处理
 * @createTime 2018年6月29日 上午11:32:50
 * @copyright: 上海投嶒网络技术有限公司
 */
public class StringCustomUtils {

    /**
     * @param list
     * @methodDesc: 功能描述: List转为String
     * @author Wu, Hua-Zheng
     * @createTime 2018年6月29日 上午11:33:38
     * @version v1.0.0
     */
    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        // 第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * Arrays.asLisvt() 返回java.util.Arrays$ArrayList，
     * 而不是ArrayList。Arrays$ArrayList和ArrayList都是继承AbstractList，
     * remove，add等method在AbstractList中是默认throw
     * UnsupportedOperationException而且不作任何操作。 ArrayList
     * override这些method来对list进行操作，但是Arrays$ArrayList没有override
     * remove(int)，add(int)等， 所以throw UnsupportedOperationException。
     *
     * @author Wu, Hua-Zheng
     * @createTime 2018年6月29日 上午11:32:50
     * @version v1.0.0
     * @copyright: 上海投嶒网络技术有限公司
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<String> stringToList(String strs) {
        String s = strs;
        if (strs.contains("["))
            s = strs.replaceFirst("\\[", "");
        if (strs.contains("]"))
            s = s.substring(0, s.length() - 1);
        String str[] = s.split(",");
        return new ArrayList(Arrays.asList(str));
    }

    /**
     * @param camelCaseName
     * @methodDesc: 功能描述: 驼峰转换为下划线，默认最后添加下划线
     * @author Wu, Hua-Zheng
     * @createTime 2018年6月29日 上午11:34:28
     * @version v1.0.0
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return String.valueOf(result.append("_")).toUpperCase();
    }


    /**
     * @param underscoreName
     * @return
     * @methodDesc: 功能描述: 下划线转换为驼峰
     * @author Wu, Hua-Zheng
     * @createTime 2018年6月29日 上午11:34:43
     * @version v1.0.0
     */
    public static String camelCaseName(String underscoreName) {
        StringBuilder result = new StringBuilder();
        underscoreName = underscoreName.toLowerCase();
        if (underscoreName != null && underscoreName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }
}
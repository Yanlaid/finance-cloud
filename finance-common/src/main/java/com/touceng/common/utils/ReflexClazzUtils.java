/**
 * @createTime: 2018年7月22日 下午9:40:58
 * @copyright: 上海投嶒网络技术有限公司
 */
package com.touceng.common.utils;

import com.touceng.common.constant.TouCengConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 反射工具类
 * @createTime 2018年7月22日 下午9:40:58
 * @copyright: 上海投嶒网络技术有限公司
 */
@Slf4j
public class ReflexClazzUtils {

    /**
     * @methodDesc: 功能描述: 获取属性值字段和注释
     * @author Wu, Hua-Zheng
     * @createTime 2018年7月22日 下午11:16:23
     * @version v1.0.0
     */
    public static String getAllFiledItems(Class<?> targetClass, Object target) {

        StringBuffer sb = new StringBuffer();
        Field[] fields = targetClass.getDeclaredFields();
        ApiModelProperty apiModel = null;
        for (Field field : fields) {
            apiModel = field.getAnnotation(ApiModelProperty.class); // 获取指定类型注解

            if (ValidatorToolUtils.isNullOrEmpty(apiModel) || ValidatorToolUtils.isNullOrEmpty(apiModel.value())) {
                continue;
            }

            // 添加字段注释和属性字段名称
            sb.append("【" + apiModel.value() + "(" + field.getName());
            try {
                // 增加属性值
                sb.append(")】----------<" + String.valueOf(field.get(target)) + ">\r\n");
            } catch (IllegalArgumentException e) {
                log.error("[ReflexClazzUtils-getAllFiledItems异常]-{}", e);
            } catch (IllegalAccessException e) {
                log.error("[ReflexClazzUtils-getAllFiledItems异常]-{}", e);
            }

        }
        return sb.toString();
    }


    /**
     * @methodDesc: 功能描述: 获取属性值字段和注释
     * @author Wu, Hua-Zheng
     * @createTime 2018年7月22日 下午11:16:23
     * @version v1.0.0
     */
    public static Map<String, String[]> getFiledStructMap(Class<?> targetClass) {

        Map<String, String[]> result = new HashMap<String, String[]>();
        List<String> fieldList = new LinkedList<String>();
        List<String> commentList = new LinkedList<String>();

        Field[] fields = targetClass.getDeclaredFields();
        ApiModelProperty apiModel = null;
        for (Field field : fields) {
            apiModel = field.getAnnotation(ApiModelProperty.class); // 获取指定类型注解

            if (ValidatorToolUtils.isNullOrEmpty(apiModel) || ValidatorToolUtils.isNullOrEmpty(apiModel.value())) {
                continue;
            }
            // 添加字段注释和属性字段名称
            //commentList.add(apiModel.value() + "[" + field.getName() + "]");
            commentList.add(apiModel.value());
            fieldList.add(field.getName());
        }

        String[] titleArray = new String[commentList.size()];
        titleArray = commentList.toArray(titleArray);

        String[] attrArray = new String[commentList.size()];
        attrArray = fieldList.toArray(attrArray);

        //字段
        result.put(TouCengConstant.EXCEL_ATTRS, attrArray);

        //注释
        result.put(TouCengConstant.EXCEL_TITLES, titleArray);

        return result;
    }


    /**
     * @methodDesc: 功能描述: 获取属性值字段和注释
     * @author Wu, Hua-Zheng
     * @createTime 2018年7月22日 下午11:16:23
     * @version v1.0.0
     */
    public static Object setFiledValue(String fieldName, String value, Object obj) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[ReflexClazzUtils-setFiledValue异常]-{}", e);
        }
        return obj;
    }


    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    public String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return (String) field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[ReflexClazzUtils-getFieldValueByFieldName异常]-{}", e);
            return null;
        }
    }

    /**
     * 根据属性名获取属性元素，包括各种安全范围和所有父类
     *
     * @param fieldName
     * @param object
     * @return
     */
    public Field getFieldByClasss(String fieldName, Object object) {
        Field field = null;
        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
                // 这里甚么都不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会进入
                e.printStackTrace();
                log.error("[ReflexClazzUtils-getFieldByClasss异常]-{}", e);
                return null;

            }
        }
        return field;

    }

}

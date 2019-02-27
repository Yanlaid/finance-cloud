package com.touceng.common.log;

import java.lang.annotation.*;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: log注解,自动记录系统日志
 * @createTime 2018年7月2日 下午5:25:39
 * @copyright: 上海投嶒网络技术有限公司
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    //描述信息
    String desc() default "";

    //操作
    String function();

    //模块
    String module();

    //项目名称
    String projectName();

    // 日志类型
    ELogTypeEnum type();
}  


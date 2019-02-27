package com.touceng.common.utils.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.touceng.common.utils.validation.impl.MobileImpl;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 类描述: 手机号码验证注解
 * @createTime 2018年3月14日 下午8:37:53
 * @copyright: 上海投嶒网络技术有限公司
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MobileImpl.class})
public @interface Mobile {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "无效的手机号码";

}

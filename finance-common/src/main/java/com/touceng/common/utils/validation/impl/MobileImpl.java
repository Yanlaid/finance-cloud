package com.touceng.common.utils.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.touceng.common.utils.validation.Mobile;
import com.touceng.common.utils.validation.regexp.PlatformRegexp;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 类描述: 手机号码验证注解实现
 * @createTime 2018年3月14日 下午8:37:53
 * @copyright: 上海投嶒网络技术有限公司
 */
public class MobileImpl implements ConstraintValidator<Mobile, String> {

    @Override
    public void initialize(Mobile constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (StringUtils.isBlank(value)) {
            return false;
        }

        if (PlatformRegexp.validate(PlatformRegexp.MOBILE, value)) {
            return true;
        }

        return false;
    }
}

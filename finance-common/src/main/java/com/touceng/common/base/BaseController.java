package com.touceng.common.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.touceng.common.exception.BusinessException;
import com.touceng.common.utils.ValidatorToolUtils;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 功能描述:(控制器类父类)
 * @createTime 2018年6月29日 上午10:54:07
 * @copyright: 上海投嶒网络技术有限公司
 */
public class BaseController {

    /**
     * @methodDesc: 功能描述: 功能描述:(使用valid注解进行字段校验)
     * @author Wu, Hua-Zheng
     * @createTime 2018年6月29日 上午10:54:45
     * @version v1.0.0
     */
    protected String valid(String msgPrefix,BindingResult bindingResult) {

        //错误信息拼接
        StringBuilder errors = new StringBuilder();
        if (bindingResult.hasErrors()) {
            if (bindingResult.getErrorCount() > 0) {
                for (FieldError error : bindingResult.getFieldErrors()) {
                    if (StringUtils.isNoneBlank(errors)) {
                        errors.append(",");
                    }
                    errors.append(error.getField()).append(":").append(error.getDefaultMessage());
                }
            }
        }

        //错误信息异常抛出
        if (ValidatorToolUtils.isNotNullOrEmpty(errors.toString())) {
            throw new BusinessException(msgPrefix+errors.toString());
        }

        return null;
    }

    /**
     * @param req
     * @methodDesc: 功能描述: 功能描述:(获取项目根目录地址)
     * @author Wu, Hua-Zheng
     * @createTime 2018年6月29日 上午10:54:26
     * @version v1.0.0
     */
    protected String getRootUrl(HttpServletRequest req) {
        StringBuffer url = req.getRequestURL();
        String contextPath = req.getContextPath();
        return url.substring(0, url.indexOf(contextPath) + contextPath.length());
    }
}

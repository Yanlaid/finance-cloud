package com.touceng.common.aspect;


import com.github.pagehelper.PageHelper;
import com.touceng.common.base.BasePageHelper;
import com.touceng.common.utils.ValidatorToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 分页切面处理
 * @createTime 2018年6月29日 上午11:29:38
 * @copyright: 上海投嶒网络技术有限公司
 */
@Slf4j
@Component
@Aspect
public class PageAspectMapper {

    @Around("execution(* com.touceng.*.mapper.*.page*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        if (null != signature) {
            // 分装mybatis的查询bean对应id
            Method method = ((MethodSignature) signature).getMethod();
            // 根据分页参数和分页返回对象自动封装成pageHelper对象
            if (method.getReturnType() == java.util.List.class) {
                for (Object object : joinPoint.getArgs()) {
                    if (object instanceof BasePageHelper) {
                        BasePageHelper basePageVo = (BasePageHelper) object;

                        // 页码和页面大小验证
                        if (basePageVo.getPageNum() > 0 && basePageVo.getPageSize() > 0) {
                            PageHelper.startPage(basePageVo.getPageNum(), basePageVo.getPageSize());
                        } else {
                            PageHelper.startPage(1, 10);
                        }

                        // 排序方式
                        if (ValidatorToolUtils.isNotNullOrEmpty(basePageVo.getSort())) {
                            // 排序字段处理 驼峰转为数据库对应字段
                            PageHelper.orderBy(basePageVo.getSort() + " " + (ValidatorToolUtils.isNotNullOrEmpty(basePageVo.getOrder()) ? basePageVo.getOrder() : "desc"));
                        }
//                        else{
//                            PageHelper.orderBy("create_time" + " " + (ValidatorToolUtils.isNotNullOrEmpty(basePageVo.getOrder()) ? basePageVo.getOrder() : "desc"));
//                        }
                    }
                }
            }
        }
        try {
            Object result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            log.error("PageAspect 异常" + e, e);
            throw e;
        }
    }
}

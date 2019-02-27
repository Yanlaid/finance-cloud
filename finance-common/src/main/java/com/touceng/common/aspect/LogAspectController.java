package com.touceng.common.aspect;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 控制层面向切面打日志
 * @createTime 2018年6月29日 上午11:28:07
 * @copyright: 上海投嶒网络技术有限公司
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class LogAspectController {

    //接口消费时间
    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    // 申明一个切点 里面是 execution表达式
    @Pointcut("execution(public * com.touceng.*.web.controller.*.*(..))")
    private void controllerAspect() {
    }

    // 请求method前打印内容
    @Before(value = "controllerAspect()")
    public void methodBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("===============请求内容==============");
        try {
            // 打印请求内容
            log.info("请求地址:" + request.getRequestURL().toString());
            log.info("请求方式:" + request.getMethod());
            log.info("请求类方法:" + joinPoint.getSignature());
            log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        } catch (Exception e) {
            log.error("###LogAspectController.class methodBefore() ### ERROR:", e);
        }
        log.info("===============请求内容===============");
    }

    // 在方法执行完结后打印返回内容
    @AfterReturning(returning = "o", pointcut = "controllerAspect()")
    public void methodAfterReturing(Object o) {
        // 处理完请求，返回内容
        log.info("--------------返回内容----------------");
        try {
            //log.info("SPEND TIME :{},Response内容:{}", (System.currentTimeMillis() - startTime.get()), JSON.toJSONString(o));
            log.info("SPEND TIME :{}", (System.currentTimeMillis() - startTime.get()));

        } catch (Exception e) {
            log.error("###LogAspectController.class methodAfterReturing() ### ERROR:{}", e,e);
        }
        log.info("--------------返回内容----------------");
    }

}
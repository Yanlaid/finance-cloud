//package com.touceng.common.aspect;
//
//import com.alibaba.fastjson.JSON;
//import com.touceng.common.log.LogAnnotation;
//import com.touceng.common.log.OperateLog;
//import com.touceng.common.response.BaseResponse;
//import com.touceng.common.response.EResultEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
///**
// * @author Wu, Hua-Zheng
// * @version v1.0.0
// * @classDesc: 功能描述: 日志处理拦截类，拦截系统中配置了LogAnnotation注解的方法
// * @createTime 2018年7月2日 下午5:36:34
// * @copyright: 上海投嶒网络技术有限公司
// */
//@Slf4j
//@Aspect
//@Component
//public class OperateAspectController {
//
//    /**
//     * @methodDesc: 功能描述: 保存用户信息
//     * @author Wu, Hua-Zheng
//     * @createTime 2018年7月2日 下午8:14:32
//     */
//    @AfterReturning(value = "execution(* com.touceng..*.*(..)) && @annotation(logAnnotation)", returning = "res")
//    public void addLog(JoinPoint joinPoint, LogAnnotation logAnnotation, BaseResponse res) {
//
//        // 判断返回结果
//        if (!EResultEnum.SUCCESS.getCode().equals(res.getCode())) {
//            log.error("[LogAspect-addLog错误]-{}", res);
//            return;
//        }
//
//
//        String methodNames = joinPoint.getSignature().getName();
//
//        Object [] args = joinPoint.getArgs();
//
//        System.out.println("The methods "+methodNames+" begins with"+Arrays.asList(args));
//
////        for (int i = 0; i < joinPoint.getArgs().length; i++) {
////            System.out.println(String.valueOf(joinPoint.getArgs()[i]));
////            //System.out.println(JSON.parseObject(String.valueOf(joinPoint.getArgs()[i]),ListPageDTO.class));
////        }
////        System.out.println(joinPoint.getSignature().getName());
//
//
//        //log.error("[LogAspect-addLog错误]-{}", JSON.parseObject(String.valueOf(joinPoint.getArgs()[0]), ListPageDTO.class));
////
////
////        log.error("[LogAspect-addLog错误]-{}",JSON.toJSONString(Arrays.toString(joinPoint.getArgs())));
//        //用户操作信息
//        OperateLog operateLog = new OperateLog();
//        operateLog.setContent(JSON.toJSONString(Arrays.toString(joinPoint.getArgs())));
//        System.out.println(JSON.toJSONString(operateLog.getContent()));
//
//
//    }
//
//
//}

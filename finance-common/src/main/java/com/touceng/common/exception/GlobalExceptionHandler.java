package com.touceng.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.touceng.common.response.BaseResponse;
import com.touceng.common.response.EResultEnum;
import com.touceng.common.response.ResultUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 全局统一异常处理
 * @createTime 2018年6月29日 下午2:39:57
 * @copyright: 上海投嶒网络技术有限公司
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = RuntimeException.class)
	@ResponseBody
	public BaseResponse goalException(Exception e) {
		
		if (e instanceof BusinessException) {
			BusinessException businessException = (BusinessException) e;
			log.info("【全局异常捕获】-error-[业务异常信息]:[{}]", e.getMessage());
			return ResultUtil.error(businessException.getCode(), businessException.getMessage());
		} else if(e instanceof Exception) {
			log.error("【全局异常捕获】-error-[系统异常信息]:[{}]", e,e);
			return ResultUtil.error(EResultEnum.ERROR.getCode(), e.getMessage());
		}else {
			log.error("【全局异常捕获】-error-[系统运行异常信息]:[{}]", e,e);
			return ResultUtil.error(EResultEnum.REQUEST_REMOTE_ERROR.getCode(), e.getMessage());
		}
	}

}

package com.touceng.common.exception;

import com.touceng.common.response.EResultEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 自定义异常
 * @createTime 2018年7月2日 下午4:03:09
 * @copyright: 上海投嶒网络技术有限公司
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7780291076367953235L;

	private int code;

	// 枚举类抛出
	public BusinessException(EResultEnum resultEnum) {
		super(resultEnum.getMsg());
		this.code = resultEnum.getCode();
	}

	// 请求参数验证失败
	public BusinessException(String msg) {
		super(msg);
		this.code = EResultEnum.ERROR.getCode();
	}

	// 自定义验证失败
	public BusinessException(int code, String msg) {
		super(msg);
		this.code = code;
	}

}

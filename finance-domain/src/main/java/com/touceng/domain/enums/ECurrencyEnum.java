package com.touceng.domain.enums;


/**
 * @classDesc: 类描述: 货币类型枚举
 * @author Wu,Hua-Zheng
 * @createTime 2018年8月28日 上午10:01:28
 * @version v1.0.0
 * @copyright: 上海投嶒网络技术有限公司
 */
public enum ECurrencyEnum {
	RMB("RMB", "人民币");

    private String code;

    private String msg;

    private ECurrencyEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
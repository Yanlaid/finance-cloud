package com.touceng.common.response;

import lombok.Data;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 返回信息创建
 * @createTime 2018年7月2日 下午3:54:41
 * @copyright: 上海投嶒网络技术有限公司
 */
@Data
public class BaseResponse {

    //错误码
    private int code;

    //信息描述
    private String msg;

    //具体的信息内容
    private Object data;
}
